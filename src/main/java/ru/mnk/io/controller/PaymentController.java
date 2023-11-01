package ru.mnk.io.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.mnk.domain.entity.Payment;
import ru.mnk.core.service.api.facade.PaymentFacade;
import ru.mnk.io.dto.ConversionDto;
import ru.mnk.io.dto.PaymentDto;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentFacade paymentFacade;

    @PostMapping("/new")
    public ResponseEntity pay(@RequestBody PaymentDto paymentDto) {
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

    @PostMapping("/emission")
    public ResponseEntity addMoney(@RequestBody PaymentDto paymentDto) {
        try {
            paymentFacade.addMoney(
                    paymentDto.amount(),
                    paymentDto.currencyCode(),
                    paymentDto.receiverId());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/conversion")
    public ResponseEntity convertMoney(@RequestBody ConversionDto paymentDto) {
        try {
            paymentFacade.convertMoney(
                    paymentDto.amount(),
                    paymentDto.currencyCodeFrom(),
                    paymentDto.currencyCodeTo(),
                    paymentDto.accountId());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
