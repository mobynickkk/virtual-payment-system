package ru.mnk.core.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.mnk.core.service.api.PaymentCallbackService;

import ru.mnk.domain.entity.Payment;

@Service
@Profile("no-integration")
public class StubPaymentCallbackService implements PaymentCallbackService {

    @Override
    public void processCallback(Payment payment) { }
}
