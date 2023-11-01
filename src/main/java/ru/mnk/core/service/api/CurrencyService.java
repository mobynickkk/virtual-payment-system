package ru.mnk.core.service.api;

import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.PaymentSystem;

import java.math.BigDecimal;

public interface CurrencyService {

    Currency getCurrency(String code, PaymentSystem paymentSystem);

    Currency addCurrency(String code, PaymentSystem paymentSystem);

    void removeCurrency(String code, PaymentSystem paymentSystem);

    /**
     * Установить обменный курс между валютами
     *
     * @param baseCurrency базовая валюта (от которой считается курс)
     * @param currency валюта
     * @param rate курс
     */
    void setExchangeRate(Currency baseCurrency, Currency currency, BigDecimal rate);

    BigDecimal getExchangeRate(Currency from, Currency to);
}
