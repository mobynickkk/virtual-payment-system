package ru.mnk.core.service.api;

import ru.mnk.core.domain.Account;

import java.math.BigDecimal;

public interface AccountService {
    Balance getBalance(Account account);
}
