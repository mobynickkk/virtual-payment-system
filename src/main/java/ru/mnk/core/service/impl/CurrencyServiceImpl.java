package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.core.domain.Currency;
import ru.mnk.core.domain.PaymentSystem;
import ru.mnk.core.repository.CurrencyRepository;
import ru.mnk.core.service.api.CurrencyService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Override
    public Currency addCurrency(String code, PaymentSystem paymentSystem) {
        Optional<Currency> existingCurrency = currencyRepository.findByCodeAndPaymentSystem(code, paymentSystem);
        if (existingCurrency.isPresent()) {
            return existingCurrency.get();
        }

        Currency currency = new Currency();
        currency.setCode(code);
        currency.setPaymentSystem(paymentSystem);
        currencyRepository.save(currency);
        return currency;
    }

    @Override
    public void removeCurrency(String code, PaymentSystem paymentSystem) {
        Optional<Currency> existingCurrency = currencyRepository.findByCodeAndPaymentSystem(code, paymentSystem);
        if (existingCurrency.isEmpty()) {
            return;
        }
        currencyRepository.delete(existingCurrency.get());
    }

    @Override
    @Transactional
    public void setExchangeRate(Currency baseCurrency, Currency currency, BigDecimal rate) {
        currency.setRate(baseCurrency.getRate().multiply(rate));
    }

    @Override
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        return to.getRate().divide(from.getRate(), new MathContext(4));
    }
}
