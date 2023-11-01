package ru.mnk.io.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.mnk.core.service.api.facade.AccountFacade;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountFacade accountFacade;

    @GetMapping("/balance")
    public ResponseEntity getBalance(Long id) {
        try {
            return ResponseEntity.ok(accountFacade.getBalance(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity createAccount(@RequestBody Long paymentSystemId) {
        try {
            return ResponseEntity.ok(accountFacade.createAccount(paymentSystemId).getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
