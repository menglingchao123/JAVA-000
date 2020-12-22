package io.mlc.transaction.serivce.provider.service;

import io.mlc.transaction.serivce.provider.dto.UpdateAccountBDTO;
import org.dromara.hmily.annotation.Hmily;

public interface BAccountService {

    @Hmily
    Boolean updateAccountB(UpdateAccountBDTO dto);

}
