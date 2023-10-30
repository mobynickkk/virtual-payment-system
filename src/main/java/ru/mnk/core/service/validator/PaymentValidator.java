package ru.mnk.core.service.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import ru.mnk.core.domain.Payment;
import ru.mnk.core.domain.PaymentSystem;

import java.math.BigDecimal;

@Service
public class PaymentValidator extends CommonStrictValidator {

    public boolean supports(Class<?> clazz) {
        return clazz != null && Payment.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        Payment payment = (Payment) target;

        if (!isCommonPaymentSystem(payment)) {
            errors.reject("different.paymentSystems");
        }

        if (isNonPositiveAmount(payment)) {
            errors.reject("negative.amount");
        }

        if (isSameSourceAndDestination(payment)) {
            errors.reject("same.source.and.destination");
        }
    }

    private boolean isCommonPaymentSystem(Payment payment) {
        PaymentSystem senderPaymentSystem = payment.getSender().getPaymentSystem();
        PaymentSystem receiverPaymentSystem = payment.getReceiver().getPaymentSystem();
        PaymentSystem currencyPaymentSystem = payment.getCurrency().getPaymentSystem();
        return senderPaymentSystem.equals(receiverPaymentSystem) && senderPaymentSystem.equals(currencyPaymentSystem);
    }

    private boolean isNonPositiveAmount(Payment payment) {
        return BigDecimal.ZERO.compareTo(payment.getAmount()) >= 0;
    }

    private boolean isSameSourceAndDestination(Payment payment) {
        return payment.getSender().equals(payment.getReceiver());
    }
}
