package ru.mnk.io.dto;

import java.math.BigDecimal;

public record PaymentDto(BigDecimal amount, String currencyCode, Long senderId, Long receiverId) { }
