package ru.mnk.core.service.api.facade;

import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.Payment;

import java.math.BigDecimal;
import java.util.Set;

public interface PaymentFacade {

    Payment transferMoney(BigDecimal amount, String currencyCode, Long senderId, Long receiverId);

    Payment transferMoney(BigDecimal amount, Currency currency, Account sender, Account receiver);

    Payment transferMoneyWithConversion(BigDecimal amount, String currencyCode, Long senderId, Long receiverId);

    Payment transferMoneyWithConversion(BigDecimal amount, Currency currency, Account sender, Account receiver);

    void convertMoney(BigDecimal amount, String from, String to, Long accountId);

    void convertMoney(BigDecimal amount, Currency from, Currency to, Account account);

    void addMoney(BigDecimal amount, String currencyCode, Long accountId);

    void addMoney(BigDecimal amount, Currency currency, Account account);

    void addMoney(BigDecimal amount, Currency currency, Set<Account> accounts);

    void addMoney(BigDecimal amount, Currency currency);
}
