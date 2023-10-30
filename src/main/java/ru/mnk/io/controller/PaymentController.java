package ru.mnk.io.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.mnk.core.domain.Payment;
import ru.mnk.core.service.api.PaymentFacade;
import ru.mnk.io.dto.PaymentDto;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentFacade paymentFacade;

    @PostMapping
    public ResponseEntity pay(PaymentDto paymentDto) {
        try {
            Payment payment = paymentFacade.transferMoney(
                    paymentDto.amount(),
                    paymentDto.currencyCode(),
                    paymentDto.senderId(),
                    paymentDto.receiverId());

            return ResponseEntity.ok(payment.getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
