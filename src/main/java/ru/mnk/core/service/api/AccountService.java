package ru.mnk.core.service.api;

import ru.mnk.core.service.api.Balance;
import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.PaymentSystem;
import ru.mnk.domain.entity.RootAccount;

public interface AccountService {

    Balance getBalance(Account account);

    Account getAccount(Long id);

    Account createAccount(PaymentSystem paymentSystem);

    RootAccount convertToRootAccount(Account account);

    void deleteAccount(Account account);
}
