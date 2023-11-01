package ru.mnk.core.service.api;

import ru.mnk.domain.entity.Account;

public interface AccountService {

    Balance getBalance(Account account);

    Account getAccount(Long id);

    Account createAccount(Long paymentSystemId);
}
