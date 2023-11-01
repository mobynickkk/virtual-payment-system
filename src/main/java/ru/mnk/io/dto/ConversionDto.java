package ru.mnk.io.dto;

import java.math.BigDecimal;

public record ConversionDto(BigDecimal amount, String currencyCodeFrom, String currencyCodeTo, Long accountId) { }
