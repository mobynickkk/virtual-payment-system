package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.PaymentSystem;
import ru.mnk.core.exceptions.NotFoundException;
import ru.mnk.domain.repository.CurrencyRepository;
import ru.mnk.core.service.api.CurrencyService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Override
    @SneakyThrows
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Currency getCurrency(String code, PaymentSystem paymentSystem) {
        Optional<Currency> existingCurrency = currencyRepository.findByCodeAndPaymentSystem(code, paymentSystem);
        if (existingCurrency.isPresent()) {
            return existingCurrency.get();
        }

        throw new NotFoundException();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
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
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeCurrency(String code, PaymentSystem paymentSystem) {
        Optional<Currency> existingCurrency = currencyRepository.findByCodeAndPaymentSystem(code, paymentSystem);
        if (existingCurrency.isEmpty()) {
            return;
        }
        currencyRepository.delete(existingCurrency.get());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void setExchangeRate(Currency baseCurrency, Currency currency, BigDecimal rate) {
        currency.setRate(baseCurrency.getRate().multiply(rate));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        return to.getRate().divide(from.getRate(), new MathContext(4));
    }
}
