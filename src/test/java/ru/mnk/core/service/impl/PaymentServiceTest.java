package ru.mnk.core.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.mnk.core.service.impl.service.PaymentServiceImpl;
import ru.mnk.domain.repository.PaymentRepository;
import ru.mnk.core.service.api.service.AccountService;
import ru.mnk.core.service.validator.PaymentValidator;
import ru.mnk.domain.entity.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    private static final Account account = new Account();
    private static final RootAccount rootAccount = new RootAccount();
    private static final PaymentRepository paymentRepository = mock(PaymentRepository.class);
    private static final PaymentValidator paymentValidator = mock(PaymentValidator.class);
    private static final Currency CUR = new Currency();

    private final AccountService accountService = new AccountServiceStub(CUR);

    private final PaymentServiceImpl paymentService = new PaymentServiceImpl(paymentRepository, paymentValidator);

    @BeforeAll
    public static void before() {
        CUR.setCode("");

        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setRootAccount(rootAccount);
        account.setPaymentSystem(paymentSystem);

        when(paymentRepository.save(any())).thenAnswer(it -> {
            Payment payment = (Payment) it.getArguments()[0];
            if (payment.getSender().equals(account)) {
                rootAccount.getReceivedPayments().add(payment);
            } else {
                account.getReceivedPayments().add(payment);
            }
            return payment;
        });
    }

    @Test
    public void testAddMoney() {
        paymentService.addMoneyToAccount(BigDecimal.TEN, CUR, account);

        assertEquals(BigDecimal.TEN, accountService.getBalance(account).getDirectBalance(CUR));
    }

}
