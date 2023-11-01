package ru.mnk.core.service.impl.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.core.service.api.CurrencyService;
import ru.mnk.core.service.api.PaymentSystemService;
import ru.mnk.core.service.api.facade.CurrencyFacade;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.PaymentSystem;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CurrencyFacadeImpl implements CurrencyFacade {
    private final CurrencyService currencyService;
    private final PaymentSystemService paymentSystemService;

    @Override
    @Transactional
    public Currency createBaseCurrency(Long paymentSystemId, String code) {
        PaymentSystem paymentSystem = paymentSystemService.getPaymentSystem(paymentSystemId);
        return currencyService.createCurrency(code, paymentSystem);
    }

    @Override
    @Transactional
    public Currency createCurrency(Long paymentSystemId, String code, String baseCurrencyCode, BigDecimal rate) {
        PaymentSystem paymentSystem = paymentSystemService.getPaymentSystem(paymentSystemId);
        Currency baseCurrency = currencyService.getCurrency(baseCurrencyCode, paymentSystem);
        Currency newCurrency = currencyService.createCurrency(code, paymentSystem);
        currencyService.setExchangeRate(baseCurrency, newCurrency, rate);
        return newCurrency;
    }

    @Override
    @Transactional
    public void deleteBaseCurrency(Long paymentSystemId, String code) {
        PaymentSystem paymentSystem = paymentSystemService.getPaymentSystem(paymentSystemId);
        currencyService.deleteCurrency(code, paymentSystem);
    }
}
