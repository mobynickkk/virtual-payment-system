package ru.mnk.io.converter;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Service;
import ru.mnk.core.domain.Currency;
import ru.mnk.core.service.api.Balance;
import ru.mnk.io.dto.BalanceDto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BalanceConverter implements Converter<Balance, BalanceDto> {
    @Override
    public BalanceDto convert(Balance source) {
        Map<String, BigDecimal> balance =
                source.getCurrencies().stream().collect(Collectors.toMap(Currency::getCode, source::getDirectBalance));
        return new BalanceDto(balance);
    }
}
