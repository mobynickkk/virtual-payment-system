package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import ru.mnk.core.domain.*;
import ru.mnk.core.repository.PaymentRepository;
import ru.mnk.core.service.api.PaymentService;
import ru.mnk.core.service.validator.PaymentValidator;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentValidator paymentValidator;

    public Payment addMoneyToAccount(BigDecimal amount, Currency currency, Account account) {
        return transferMoney(amount, currency, getRootAccount(account), account);
    }

    public Payment removeMoneyFromAccount(BigDecimal amount, Currency currency, Account account) {
        return transferMoney(amount, currency, account, getRootAccount(account));
    }

    @SneakyThrows
    public Payment transferMoney(BigDecimal amount, Currency currency, Account sender, Account receiver) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setCurrency(currency);
        payment.setSender(sender);
        payment.setReceiver(receiver);
        paymentValidator.validate(payment);
        paymentRepository.save(payment);
        return payment;
    }

    private RootAccount getRootAccount(Account account) {
        return account.getPaymentSystem().getRootAccount();
    }
}
