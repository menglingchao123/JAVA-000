package io.mlc.transaction.serivce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_freeze_account")
public class FreezeAccount {
    @TableField("ID")
    @TableId(type = IdType.UUID)
    private String id;
    @TableField("ACCOUNT_ID")
    private String accountId;
    @TableField("DOLLAR")
    private BigDecimal dollar;
    @TableField("RMB")
    private BigDecimal rmb;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("update_time")
    private LocalDateTime updateTime;
    @TableField("DELETE_FLAG")
    private Integer deleteFlag;
}
