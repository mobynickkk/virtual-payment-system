package ru.mnk.io.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.mnk.domain.entity.Account;
import ru.mnk.core.service.api.AccountService;
import ru.mnk.core.service.api.Balance;
import ru.mnk.io.converter.BalanceConverter;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final BalanceConverter balanceConverter;

    @GetMapping("/balance")
    public ResponseEntity getBalance(Long id) {
        try {
            Account account = accountService.getAccount(id);
            Balance balance = accountService.getBalance(account);
            account.setLastCalculatedBalance(balance);
            return ResponseEntity.ok(balanceConverter.convert(balance));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity createAccount(@RequestBody Long paymentSystemId) {
        try {
            Account account = accountService.createAccount(paymentSystemId);
            return ResponseEntity.ok(account.getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
