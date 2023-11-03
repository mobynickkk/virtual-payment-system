package ru.mnk.core.service.api;

import ru.mnk.domain.entity.Payment;

public interface PaymentCallbackService {

    void processCallback(Payment payment);
}
