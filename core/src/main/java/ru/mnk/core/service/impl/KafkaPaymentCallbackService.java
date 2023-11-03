package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ru.mnk.core.service.api.PaymentCallbackService;
import ru.mnk.domain.entity.Payment;
import ru.mnk.io.converter.PaymentConverter;
import ru.mnk.io.exceptions.IntegrationException;
import ru.mnk.io.kafka.PaymentProducer;

@Service
@AllArgsConstructor
@Profile("kafka")
public class KafkaPaymentCallbackService implements PaymentCallbackService {
    private final PaymentProducer paymentProducer;
    private final PaymentConverter paymentConverter;

    @Override
    @SneakyThrows
    public void processCallback(Payment payment) {
        try {
            paymentProducer.sendMessage(paymentConverter.convert(payment));
        } catch (Exception e) {
            throw new IntegrationException();
        }
    }
}
