package ru.mnk.core.service.api;

import ru.mnk.core.domain.Account;
import ru.mnk.core.domain.Currency;
import ru.mnk.core.domain.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    Payment addMoneyToAccount(BigDecimal amount, Currency currency, Account account);

    Payment removeMoneyFromAccount(BigDecimal amount, Currency currency, Account account);

    Payment transferMoney(BigDecimal amount, Currency currency, Account sender, Account receiver);
}
