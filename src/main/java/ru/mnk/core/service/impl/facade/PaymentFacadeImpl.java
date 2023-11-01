package ru.mnk.core.service.impl.facade;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.core.service.api.AccountService;
import ru.mnk.core.service.api.CurrencyService;
import ru.mnk.core.service.api.PaymentService;
import ru.mnk.core.service.api.facade.PaymentCallbackService;
import ru.mnk.core.service.api.facade.PaymentFacade;
import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.Payment;
import ru.mnk.core.exceptions.NotEnoughMoneyException;
import ru.mnk.core.service.api.*;

import java.math.BigDecimal;
import java.util.Set;

@Service
@AllArgsConstructor
public class PaymentFacadeImpl implements PaymentFacade {
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final PaymentService paymentService;
    private final PaymentCallbackService paymentCallbackService;

    @Override
    @Transactional
    @SneakyThrows
    public Payment transferMoney(BigDecimal amount, String currencyCode, Long senderId, Long receiverId) {
        Account sender = accountService.getAccount(senderId);
        Account receiver = accountService.getAccount(receiverId);
        Currency currency = currencyService.getCurrency(currencyCode, sender.getPaymentSystem());
        return transferMoney(amount, currency, sender, receiver);
    }

    @Override
    @Transactional
    @SneakyThrows
    public Payment transferMoney(BigDecimal amount, Currency currency, Account sender, Account receiver) {
        Balance balance = accountService.getBalance(sender);
        BigDecimal availableBalance = balance.getDirectBalance(currency);
        if (amount.compareTo(availableBalance) > 0) {
            throw new NotEnoughMoneyException();
        }
        Payment payment = paymentService.transferMoney(amount, currency, sender, receiver);
        paymentCallbackService.processCallback(payment);
        return payment;
    }

    @Override
    public Payment transferMoneyWithConversion(BigDecimal amount, String currencyCode, Long senderId, Long receiverId) {
        Account sender = accountService.getAccount(senderId);
        Account receiver = accountService.getAccount(receiverId);
        Currency currency = currencyService.getCurrency(currencyCode, sender.getPaymentSystem());
        return transferMoneyWithConversion(amount, currency, sender, receiver);
    }

    @Override
    @Transactional
    @SneakyThrows
    public Payment transferMoneyWithConversion(BigDecimal amount, Currency currency, Account sender, Account receiver) {
        Balance balance = accountService.getBalance(sender);
        BigDecimal availableBalance = balance.getBalance(currency, currencyService);
        if (amount.compareTo(availableBalance) > 0) {
            throw new NotEnoughMoneyException();
        }

        BigDecimal delta = amount.subtract(balance.getDirectBalance(currency));

        for (Currency cur : balance.getCurrencies()) {
            if (cur.equals(currency)) {
                continue;
            }
            if (delta.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            BigDecimal sum = currencyService.getExchangeRate(currency, cur).multiply(delta)
                    .min(balance.getDirectBalance(cur));
            convertMoney(sum, cur, currency, sender);
            delta = delta.subtract(sum.multiply(currencyService.getExchangeRate(cur, currency)));
        }

        Payment payment = paymentService.transferMoney(amount, currency, sender, receiver);
        paymentCallbackService.processCallback(payment);
        return payment;
    }

    @Override
    @Transactional
    public void convertMoney(BigDecimal amount, String from, String to, Long accountId) {
        Account account = accountService.getAccount(accountId);
        Currency currencyFrom = currencyService.getCurrency(from, account.getPaymentSystem());
        Currency currencyTo = currencyService.getCurrency(to, account.getPaymentSystem());
        convertMoney(amount, currencyFrom, currencyTo, account);
    }

    @Override
    @Transactional
    @SneakyThrows
    public void convertMoney(BigDecimal amount, Currency from, Currency to, Account account) {
        Balance balance = accountService.getBalance(account);
        BigDecimal availableBalance = balance.getDirectBalance(from);
        if (amount.compareTo(availableBalance) > 0) {
            throw new NotEnoughMoneyException();
        }
        BigDecimal convertedAmount = amount.multiply(currencyService.getExchangeRate(from, to));
        paymentService.removeMoneyFromAccount(amount, from, account);
        paymentService.addMoneyToAccount(convertedAmount, to, account);
    }

    @Override
    @Transactional
    public void addMoney(BigDecimal amount, String currencyCode, Long accountId) {
        Account account = accountService.getAccount(accountId);
        Currency currency = currencyService.getCurrency(currencyCode, account.getPaymentSystem());
        addMoney(amount, currency, account);
    }

    @Override
    @Transactional
    public void addMoney(BigDecimal amount, Currency currency, Account account) {
        paymentService.addMoneyToAccount(amount, currency, account);
    }

    @Override
    @Transactional
    public void addMoney(BigDecimal amount, Currency currency, Set<Account> accounts) {
        accounts.forEach(it -> paymentService.addMoneyToAccount(amount, currency, it));
    }

    @Override
    @Transactional
    public void addMoney(BigDecimal amount, Currency currency) {
        currency.getPaymentSystem().getAccounts()
                .forEach(it -> paymentService.addMoneyToAccount(amount, currency, it));
    }
}
