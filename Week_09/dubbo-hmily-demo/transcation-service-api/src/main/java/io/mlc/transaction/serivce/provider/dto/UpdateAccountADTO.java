package io.mlc.transaction.serivce.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountADTO implements Serializable {
    private String bBccountId;
    /**账户id**/
    private String aAccountId;
    /**人民币**/
    private BigDecimal rmb;
}
