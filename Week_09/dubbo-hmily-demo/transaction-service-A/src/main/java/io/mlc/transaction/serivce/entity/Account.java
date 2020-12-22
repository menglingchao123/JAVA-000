package io.mlc.transaction.serivce.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("TB_ACCOUNT")
public class Account {
    @TableField("ID")
    private String id;
    @TableField("USER_ID")
    private String userId;
    @TableField("DOLLAR")
    private BigDecimal dollar;
    @TableField("RMB")
    private BigDecimal rmb;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("update_time")
    private LocalDateTime updateTime;
}
