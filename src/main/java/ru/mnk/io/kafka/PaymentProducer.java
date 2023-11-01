package ru.mnk.io.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.mnk.io.dto.PaymentDto;

@Service
@AllArgsConstructor
public class PaymentProducer {
    @Value("${producingTopic.name}")
    private String paymentTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(PaymentDto paymentDto) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(paymentDto);
        kafkaTemplate.send(paymentTopic, orderAsMessage);
    }
}
