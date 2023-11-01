package ru.mnk.core.service.impl;

import org.springframework.stereotype.Service;
import ru.mnk.core.service.api.facade.PaymentCallbackService;

import ru.mnk.domain.entity.Payment;

@Service
public class StubPaymentCallbackService implements PaymentCallbackService {

    @Override
    public void processCallback(Payment payment) { }
}
