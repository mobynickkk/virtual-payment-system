package ru.mnk.core.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.mnk.core.exceptions.ValidationException;
import ru.mnk.domain.entity.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PaymentValidatorTest  {
    private static final Account account1 = new Account();
    private static final Account account2 = new Account();
    private static final Currency CUR = new Currency();
    private final PaymentValidator paymentValidator = new PaymentValidator();

    @BeforeEach
    public void before() {
        CUR.setCode("");

        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(Long.parseLong("1"));
        account1.setPaymentSystem(paymentSystem);
        account1.setId(Long.parseLong("1"));
        account2.setPaymentSystem(paymentSystem);
        account2.setId(Long.parseLong("2"));
        CUR.setPaymentSystem(paymentSystem);
    }

    @Test

    public void testOkPayment() throws ValidationException {
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.TEN);
        payment.setCurrency(CUR);
        payment.setSender(account1);
        payment.setReceiver(account2);
        payment.setStatus(Status.DONE);

        paymentValidator.validate(payment);
    }

    @Test
    public void testDifferentPaymentSystem() {
        CUR.setPaymentSystem(new PaymentSystem());
        CUR.getPaymentSystem().setId(Long.parseLong("2"));

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.TEN);
        payment.setCurrency(CUR);
        payment.setSender(account1);
        payment.setReceiver(account2);
        payment.setStatus(Status.DONE);

        assertThrows(ValidationException.class, () -> {paymentValidator.validate(payment);});
    }

    @Test
    public void testZeroAmount() {
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.ZERO);
        payment.setCurrency(CUR);
        payment.setSender(account1);
        payment.setReceiver(account2);
        payment.setStatus(Status.DONE);

        assertThrows(ValidationException.class, () -> {paymentValidator.validate(payment);});
    }

    @Test
    public void testSameSourceAndDestination() {
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.TEN);
        payment.setCurrency(CUR);
        payment.setSender(account1);
        payment.setReceiver(account1);
        payment.setStatus(Status.DONE);

        assertThrows(ValidationException.class, () -> {paymentValidator.validate(payment);});
    }
}
