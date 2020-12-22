package io.mlc.transaction.serivce.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.mlc.transaction.serivce.entity.Account;
import io.mlc.transaction.serivce.provider.dto.UpdateAccountADTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface AccountDao extends BaseMapper<Account> {

    @Update("update TB_ACCOUNT set DOLLAR = DOLLAR - #{dollar},RMB = RMB + #{rmb},update_time = #{updateTime} where DOLLAR > #{dollar} and ID = #{id}")
    int updateDollarAccount(Account account);

    @Update("update TB_ACCOUNT set DOLLAR = DOLLAR + #{dollar},RMB = RMB - #{rmb},update_time = #{updateTime} where ID = #{id}")
    int rollbackAccount(Account account);
}
