package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.core.entities.*;
import ru.mnk.domain.repository.PaymentRepository;
import ru.mnk.core.service.api.PaymentService;
import ru.mnk.core.service.validator.PaymentValidator;
import ru.mnk.domain.entity.*;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentValidator paymentValidator;

    @Transactional(propagation = Propagation.MANDATORY)
    public Payment addMoneyToAccount(BigDecimal amount, Currency currency, Account account) {
        return transferMoney(amount, currency, getRootAccount(account), account);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Payment removeMoneyFromAccount(BigDecimal amount, Currency currency, Account account) {
        return transferMoney(amount, currency, account, getRootAccount(account));
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.MANDATORY)
    public Payment transferMoney(BigDecimal amount, Currency currency, Account sender, Account receiver) {
        Payment payment = new Payment();
        payment.setStatus(Status.IN_PROCESS);
        payment.setAmount(amount);
        payment.setCurrency(currency);
        payment.setSender(sender);
        payment.setReceiver(receiver);
        paymentValidator.validate(payment);
        paymentRepository.save(payment);
        payment.setStatus(Status.DONE);
        return payment;
    }

    private RootAccount getRootAccount(Account account) {
        return account.getPaymentSystem().getRootAccount();
    }
}
