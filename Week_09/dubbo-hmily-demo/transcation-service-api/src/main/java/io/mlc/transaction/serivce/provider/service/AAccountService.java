package io.mlc.transaction.serivce.provider.service;

import io.mlc.transaction.serivce.provider.dto.UpdateAccountADTO;
import org.dromara.hmily.annotation.Hmily;

public interface AAccountService {

    @Hmily
    Boolean updateAccountA(UpdateAccountADTO dto);

}
