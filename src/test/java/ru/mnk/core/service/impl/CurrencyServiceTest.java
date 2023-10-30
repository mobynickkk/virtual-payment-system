package ru.mnk.core.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.repository.CurrencyRepository;
import ru.mnk.core.service.api.CurrencyService;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {
    private static final Currency RUB = new Currency();
    private static final Currency USD = new Currency();
    private static final Currency CNY = new Currency();
    private static final BigDecimal R4D = new BigDecimal(92);
    private static final BigDecimal R4Y = new BigDecimal(16);

    private final CurrencyRepository currencyRepository = mock(CurrencyRepository.class);

    private final CurrencyService currencyService = new CurrencyServiceImpl(currencyRepository);

    @BeforeAll
    public static void before() {
        RUB.setCode("RUB");
        USD.setCode("USD");
        CNY.setCode("CNY");
    }

    @Test
    public void testExchangeRate() {
        currencyService.setExchangeRate(USD, RUB, R4D);
        currencyService.setExchangeRate(RUB, CNY, BigDecimal.ONE.divide(R4Y, new MathContext(3)));

        assertEquals(0, new BigDecimal("5.75").subtract(currencyService.getExchangeRate(USD, CNY)).signum());
    }
}
