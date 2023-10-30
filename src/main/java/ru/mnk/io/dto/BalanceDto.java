package ru.mnk.io.dto;

import java.math.BigDecimal;
import java.util.Map;

public record BalanceDto(Map<String, BigDecimal> balances) { }
