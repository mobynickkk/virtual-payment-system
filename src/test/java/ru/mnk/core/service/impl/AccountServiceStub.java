package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.Payment;
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

    @Override
    public Account createAccount(Long paymentSystemId) {
        return null;
    }
}
