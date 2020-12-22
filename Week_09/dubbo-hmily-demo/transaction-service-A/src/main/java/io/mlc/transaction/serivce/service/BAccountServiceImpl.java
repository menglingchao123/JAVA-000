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
import org.apache.ibatis.annotations.Update;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BAccountServiceImpl implements BAccountService {

    @DubboReference(version = "1.0.0",timeout = 2000,retries = 0,interfaceName = "aAccountService")
    private AAccountService aAccountService;
    @Resource
    private AccountDao accountDao;
    @Resource
    private FreezeAccountDao freezeAccountDao;

    @HmilyTCC(cancelMethod = "cancelNested",confirmMethod = "confirmNested")
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAccountB(UpdateAccountBDTO dto){
        //账户减少dollar
        BigDecimal dollar = BigDecimalUtils.divide(dto.getRmb(), new BigDecimal(7));
        int count = accountDao.updateRmbAccount(Account.builder().rmb(dto.getRmb()).dollar(dollar).id(dto.getBAccountId()).build());
        if (count<=0) throw new RuntimeException("账户余额不足");
        //插入账户冻结表
        FreezeAccount freezeAccount = FreezeAccount.builder()
                .accountId(dto.getBAccountId())
                .rmb(dto.getRmb())
                .dollar(dollar)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        freezeAccountDao.insert(freezeAccount);
        //调用A服务转账
        aAccountService.updateAccountA(UpdateAccountADTO.builder().aAccountId(dto.getAAccountId()).bBccountId(dto.getBAccountId()).rmb(dto.getRmb()).build());
        return Boolean.TRUE;
    }


    @Transactional(rollbackFor = Exception.class)
    public void confirmNested(UpdateAccountADTO dto){
        //删除冻结表
        freezeAccountDao.deleteById(dto.getBBccountId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelNested(UpdateAccountADTO dto){
        //删除冻结表
        freezeAccountDao.deleteById(dto.getBBccountId());
        //还原账户表
        Account account = Account.builder().rmb(dto.getRmb()).dollar(BigDecimalUtils.divide(dto.getRmb(), new BigDecimal(7))).id(dto.getBBccountId()).build();
        accountDao.rollRmbback(account);
    }
}
