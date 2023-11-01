package ru.mnk.core.service.api.facade;

import ru.mnk.domain.entity.Currency;

import java.math.BigDecimal;

public interface CurrencyFacade {
    Currency createBaseCurrency(Long paymentSystemId, String code);

    Currency createCurrency(Long paymentSystemId, String code, String baseCurrencyCode, BigDecimal rate);

    void deleteBaseCurrency(Long paymentSystemId, String code);
}
