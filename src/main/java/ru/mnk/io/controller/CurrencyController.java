package ru.mnk.io.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mnk.core.service.api.facade.CurrencyFacade;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currencies")
@AllArgsConstructor
public class CurrencyController {
    private final CurrencyFacade currencyFacade;

    @PostMapping("/new")
    public ResponseEntity createBaseCurrency(Long paymentSystemId, String code) {
        try {
            currencyFacade.createBaseCurrency(paymentSystemId, code);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/base/new")
    public ResponseEntity createCurrency(Long paymentSystemId, String code, String baseCurrencyCode, BigDecimal rate) {
        try {
            currencyFacade.createCurrency(paymentSystemId, code, baseCurrencyCode, rate);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity deleteCurrency(Long paymentSystemId, String code) {
        try {
            currencyFacade.deleteBaseCurrency(paymentSystemId, code);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
