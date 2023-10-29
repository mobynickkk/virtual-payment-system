package ru.mnk.core.service.api;

import ru.mnk.core.domain.Currency;
import ru.mnk.core.domain.PaymentSystem;

import java.math.BigDecimal;

public interface CurrencyService {

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
