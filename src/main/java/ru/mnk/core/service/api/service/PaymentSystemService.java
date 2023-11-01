package ru.mnk.core.service.api.service;

import ru.mnk.domain.entity.PaymentSystem;

public interface PaymentSystemService {
    PaymentSystem createNewPaymentSystem();

    PaymentSystem getPaymentSystem(Long id);
}
