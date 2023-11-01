package ru.mnk.io.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.mnk.core.service.api.facade.PaymentSystemFacade;
import ru.mnk.domain.entity.RootAccount;

import java.util.Optional;

@RestController
@RequestMapping("/systems")
@AllArgsConstructor
public class PaymentSystemController {
    private final PaymentSystemFacade paymentSystemFacade;

    @PostMapping("/new")
    public ResponseEntity createNewPaymentSystem() {
        try {
            return ResponseEntity.ok(paymentSystemFacade.createPaymentSystem().getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/root")
    public ResponseEntity getRootAccount(Long id) {
        try {
            Long rootId = Optional.of(paymentSystemFacade.getRootAccount(id))
                    .map(RootAccount::getId).orElse(0L);
            return ResponseEntity.ok(rootId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/root")
    public ResponseEntity setRootAccount(Long paymentSystemId, Long accountId) {
        try {
            paymentSystemFacade.setRootAccount(paymentSystemId, accountId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
