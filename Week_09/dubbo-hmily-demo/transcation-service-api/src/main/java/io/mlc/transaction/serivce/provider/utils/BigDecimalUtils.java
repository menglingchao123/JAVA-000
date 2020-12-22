package io.mlc.transaction.serivce.provider.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static BigDecimal divide(BigDecimal a1,BigDecimal a2){
        return a1.divide(a2,2,BigDecimal.ROUND_HALF_UP);
    }

}
