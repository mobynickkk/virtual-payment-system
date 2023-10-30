package ru.mnk.core.service.api;

import ru.mnk.core.domain.Account;

public interface AccountService {

    Balance getBalance(Account account);
}
