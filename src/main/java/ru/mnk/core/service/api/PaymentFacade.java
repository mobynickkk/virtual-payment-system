package ru.mnk.core.service.api;

import ru.mnk.core.domain.Account;
import ru.mnk.core.domain.Currency;
import ru.mnk.core.domain.Payment;

import java.math.BigDecimal;
import java.util.Set;

public interface PaymentFacade {

    Payment transferMoney(BigDecimal amount, Currency currency, Account from, Account to);

    Payment transferMoneyWithConversion(BigDecimal amount, Currency currency, Account from, Account to);

    void convertMoney(BigDecimal amount, Currency from, Currency to, Account account);

    void addMoney(BigDecimal amount, Currency currency, Account account);

    void addMoney(BigDecimal amount, Currency currency, Set<Account> account);

    void addMoney(BigDecimal amount, Currency currency);
}
