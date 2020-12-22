package io.mlc.transaction.serivce.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.mlc.transaction.serivce.entity.FreezeAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FreezeAccountDao extends BaseMapper<FreezeAccount> {

    @Update("update tb_freeze_account set DELETE_FLAG = 1 where ACCOUNT_ID = #{accountId}")
    int deleteById(@Param("accountId") String accountId);
}
