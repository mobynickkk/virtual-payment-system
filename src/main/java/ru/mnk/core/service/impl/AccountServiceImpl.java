package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.domain.entity.*;
import ru.mnk.core.exceptions.NotFoundException;
import ru.mnk.domain.repository.AccountRepository;
import ru.mnk.core.service.api.AccountService;
import ru.mnk.core.service.api.Balance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Balance getBalance(Account account) {
        Set<Payment> receivedPayments = account.getReceivedPayments();
        Set<Payment> sentPayments = account.getSentPayments();
        Map<Currency, BigDecimal> receives = getBalanceMap(receivedPayments);
        Map<Currency, BigDecimal> sents = getBalanceMap(sentPayments);
        Balance positiveBalance = new Balance(receives);
        Balance negativeBalance = new Balance(sents).negate();
        return positiveBalance.add(negativeBalance);
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    @Transactional
    public Account createAccount(PaymentSystem paymentSystem) {
        Account account = new Account();
        account.setPaymentSystem(paymentSystem);
        account.setStatus(Status.WORKING);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public RootAccount convertToRootAccount(Account account) {
        RootAccount rootAccount = new RootAccount(account);
        accountRepository.delete(account);
        accountRepository.flush();
        accountRepository.save(rootAccount);
        return rootAccount;
    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    private static HashMap<Currency, BigDecimal> getBalanceMap(Set<Payment> receivedPayments) {
        return receivedPayments.stream()
                .reduce(new HashMap<>(),
                        (acc, payment) -> {
                            acc.merge(payment.getCurrency(), payment.getAmount(), BigDecimal::add);
                            return acc;
                        },
                        (m, m2) -> {
                            m.putAll(m2);
                            return m;
                        });
    }
}
