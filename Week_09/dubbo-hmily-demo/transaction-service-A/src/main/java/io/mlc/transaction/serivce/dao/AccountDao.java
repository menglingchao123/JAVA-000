package io.mlc.transaction.serivce.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.mlc.transaction.serivce.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountDao extends BaseMapper<Account> {

    @Update("update TB_ACCOUNT set RMB = RMB - #{rmb}, DOLLAR = DOLLAR + #{dollar} ,update_time = now() where id = #{id} and RMB > #{rmb}")
    int updateRmbAccount(Account account);

    @Update("update TB_ACCOUNT set RMB = RMB + #{rmb},update_time = now(),DOLLAR = DOLLAR - #{dollar} where id = #{id} ")
    int rollRmbback(Account account);

}
