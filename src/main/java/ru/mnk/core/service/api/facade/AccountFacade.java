package ru.mnk.core.service.api.facade;

import ru.mnk.domain.entity.Account;
import ru.mnk.io.dto.BalanceDto;

public interface AccountFacade {

    BalanceDto getBalance(Long id);

    Account createAccount(Long paymentSystemId);
}
