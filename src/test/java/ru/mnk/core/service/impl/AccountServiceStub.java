package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ru.mnk.core.domain.Account;
import ru.mnk.core.domain.Currency;
import ru.mnk.core.domain.Payment;
import ru.mnk.core.service.api.AccountService;
import ru.mnk.core.service.api.Balance;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class AccountServiceStub implements AccountService {
    private Currency currency;

    @Override
    public Balance getBalance(Account account) {
        return new Balance(Map.of(
                currency,
                account.getReceivedPayments()
                        .stream()
                        .map(Payment::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    @Override
    public Account getAccount(Long id) {
        return null;
    }
}
