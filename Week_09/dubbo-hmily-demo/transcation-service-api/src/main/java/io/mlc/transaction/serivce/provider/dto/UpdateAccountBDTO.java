package io.mlc.transaction.serivce.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountBDTO implements Serializable {
    /**用户id**/
    private String aAccountId;
    /**用户id**/
    private String bAccountId;
    /**美元**/
    private BigDecimal rmb;
}
