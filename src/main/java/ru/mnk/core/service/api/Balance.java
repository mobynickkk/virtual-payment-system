package ru.mnk.core.service.api;

import ru.mnk.domain.entity.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record Balance(Map<Currency, BigDecimal> balancesInCurrencies) {

    public BigDecimal getDirectBalance(Currency currency) {
        return balancesInCurrencies.getOrDefault(currency, BigDecimal.ZERO);
    }

    public BigDecimal getBalance(Currency currency, CurrencyService currencyService) {
        BigDecimal directBalance = balancesInCurrencies.getOrDefault(currency, BigDecimal.ZERO);
        BigDecimal convertedBalance = balancesInCurrencies.keySet()
                .stream()
                .filter(it -> !currency.equals(it))
                .reduce(BigDecimal.ZERO,
                        (acc, cur) -> acc.add(getConvertedBalance(cur, currency, currencyService)),
                        BigDecimal::add);
        return directBalance.add(convertedBalance);
    }

    private BigDecimal getConvertedBalance(Currency from, Currency to, CurrencyService currencyService) {
        return balancesInCurrencies.get(from).multiply(currencyService.getExchangeRate(from, to));
    }

    public Balance add(Balance other) {
        Map<Currency, BigDecimal> balancesInCurrencies = new HashMap<>();
        getAllCurrencies(other).forEach(currency -> {
            balancesInCurrencies.put(currency, this.getDirectBalance(currency).add(other.getDirectBalance(currency)));
        });
        return new Balance(balancesInCurrencies);
    }

    public Balance negate() {
        balancesInCurrencies.forEach((key, value) -> balancesInCurrencies.put(key, value.negate()));
        return this;
    }

    public Set<Currency> getCurrencies() {
        return balancesInCurrencies.keySet();
    }

    private Set<Currency> getAllCurrencies(Balance other) {
        Set<Currency> sum = new HashSet<>(this.balancesInCurrencies.keySet());
        sum.addAll(other.balancesInCurrencies.keySet());
        return sum;
    }
}
