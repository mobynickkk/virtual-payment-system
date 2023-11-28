package ru.mnk.io.api;

import java.math.BigDecimal;

public interface PaymentConsumerService {

    void processPayment(BigDecimal amount, String currencyCode, Long senderId, Long receiverId);
}
