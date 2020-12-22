package io.mlc.transaction.serivce.service;

import io.mlc.transaction.serivce.dao.AccountDao;
import io.mlc.transaction.serivce.dao.FreezeAccountDao;
import io.mlc.transaction.serivce.entity.Account;
import io.mlc.transaction.serivce.entity.FreezeAccount;
import io.mlc.transaction.serivce.provider.dto.UpdateAccountADTO;
import io.mlc.transaction.serivce.provider.dto.UpdateAccountBDTO;
import io.mlc.transaction.serivce.provider.service.AAccountService;
import io.mlc.transaction.serivce.provider.service.BAccountService;
import io.mlc.transaction.serivce.provider.utils.BigDecimalUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@DubboService(version = "1.0.0",retries = 0,timeout = 2000,interfaceName = "aAccountService")
public class AAccountServiceImpl implements AAccountService {

    @Resource
    private AccountDao accountDao;
    @Resource
    private FreezeAccountDao freezeAccountDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @HmilyTCC(confirmMethod = "confirm",cancelMethod = "cancel")
    public Boolean updateAccountA(UpdateAccountADTO dto) {
        //账户表修改
        BigDecimal dollar = BigDecimalUtils.divide(dto.getRmb(), new BigDecimal(7));
        Account account = Account.builder().dollar(dollar)
                .id(dto.getAAccountId())
                .rmb(dto.getRmb())
                .updateTime(LocalDateTime.now()).build();
        int count = accountDao.updateDollarAccount(account);
        if (count<=0)throw new RuntimeException("账户余额不足");
        //账户冻结表插入记录
        FreezeAccount freezeAccount = FreezeAccount.builder()
                .accountId(dto.getAAccountId())
                .dollar(dollar)
                .rmb(dto.getRmb())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        freezeAccountDao.insert(freezeAccount);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirm(UpdateAccountADTO dto){
        //账户冻结表记录删除
        freezeAccountDao.deleteById(dto.getAAccountId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(UpdateAccountADTO dto){
        //账户冻结表删除
        freezeAccountDao.deleteById(dto.getAAccountId());
        //账户表还原
        BigDecimal dollar = BigDecimalUtils.divide(dto.getRmb(), new BigDecimal(7));
        Account account = Account.builder().dollar(dollar)
                .id(dto.getAAccountId())
                .rmb(dto.getRmb())
                .updateTime(LocalDateTime.now()).build();
        accountDao.rollbackAccount(account);
    }
}
