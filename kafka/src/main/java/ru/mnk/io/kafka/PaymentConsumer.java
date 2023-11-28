package ru.mnk.io.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import ru.mnk.io.api.PaymentConsumerService;
import ru.mnk.io.dto.PaymentDto;

@Service
@AllArgsConstructor
public class PaymentConsumer {

    private static final String orderTopic = "${consumingTopic.name}";

    private final ObjectMapper objectMapper;
    private final PaymentConsumerService paymentConsumerService;

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        PaymentDto paymentDto = objectMapper.readValue(message, PaymentDto.class);
        paymentConsumerService.processPayment(
                paymentDto.amount(),
                paymentDto.currencyCode(),
                paymentDto.senderId(),
                paymentDto.receiverId());
    }
}
