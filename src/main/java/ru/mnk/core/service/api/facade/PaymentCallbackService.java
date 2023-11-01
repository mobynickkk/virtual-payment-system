package ru.mnk.core.service.api.facade;

import ru.mnk.domain.entity.Payment;

public interface PaymentCallbackService {

    void processCallback(Payment payment);
}
