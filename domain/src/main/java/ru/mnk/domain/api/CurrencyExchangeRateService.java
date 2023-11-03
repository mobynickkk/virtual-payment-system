package ru.mnk.domain.api;

import ru.mnk.domain.entity.Currency;

import java.math.BigDecimal;

public interface CurrencyExchangeRateService {

    BigDecimal getExchangeRate(Currency from, Currency to);
}
