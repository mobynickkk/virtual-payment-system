package ru.mnk.core.service.api.service;

import ru.mnk.core.service.api.Balance;
import ru.mnk.domain.entity.Account;

public interface AccountService {

    Balance getBalance(Account account);

    Account getAccount(Long id);

    Account createAccount(Long paymentSystemId);
}
