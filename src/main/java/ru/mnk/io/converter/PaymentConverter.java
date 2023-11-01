package ru.mnk.io.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import ru.mnk.domain.entity.Payment;
import ru.mnk.io.dto.PaymentDto;

@Service
public class PaymentConverter implements Converter<Payment, PaymentDto> {
    @Override
    public PaymentDto convert(Payment source) {
        return new PaymentDto(
                source.getAmount(),
                source.getCurrency().getCode(),
                source.getSender().getId(),
                source.getReceiver().getId());
    }
}
