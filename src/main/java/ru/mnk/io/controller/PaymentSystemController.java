package ru.mnk.io.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.mnk.core.service.api.PaymentSystemService;
import ru.mnk.domain.entity.RootAccount;

import java.util.Optional;

@RestController
@RequestMapping("/systems")
@AllArgsConstructor
public class PaymentSystemController {
    private final PaymentSystemService paymentSystemService;

    @PostMapping("/new")
    public ResponseEntity createNewPaymentSystem() {
        try {
            return ResponseEntity.ok(paymentSystemService.createNewPaymentSystem().getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/root")
    public ResponseEntity getRootAccount(Long id) {
        try {
            Long rootId = Optional.of(paymentSystemService.getPaymentSystem(id).getRootAccount())
                    .map(RootAccount::getId).orElse(0l);
            return ResponseEntity.ok(rootId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/root")
    public ResponseEntity setRootAccount(Long paymentSystemId, Long accountId) {
        try {
            Long rootId = Optional.of(paymentSystemService.getPaymentSystem(paymentSystemId).getRootAccount())
                    .map(RootAccount::getId).orElse(0l);
            return ResponseEntity.ok(rootId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
