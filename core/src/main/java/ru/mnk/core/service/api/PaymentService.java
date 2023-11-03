package ru.mnk.core.service.api;

import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    Payment addMoneyToAccount(BigDecimal amount, Currency currency, Account account);

    Payment removeMoneyFromAccount(BigDecimal amount, Currency currency, Account account);

    Payment transferMoney(BigDecimal amount, Currency currency, Account sender, Account receiver);
}
