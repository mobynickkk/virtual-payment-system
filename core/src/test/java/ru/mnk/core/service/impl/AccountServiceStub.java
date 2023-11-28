package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ru.mnk.domain.entity.*;
import ru.mnk.core.service.api.AccountService;
import ru.mnk.domain.transients.Balance;

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
    public Account createAccount(PaymentSystem paymentSystem) {
        return null;
    }

    @Override
    public RootAccount convertToRootAccount(Account account) {
        return null;
    }

    @Override
    public void deleteAccount(Account account) {

    }
}
