package ru.mnk.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "currencies")
public class Currency {
    @Id
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentSystemId", nullable = false)
    private PaymentSystem paymentSystem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (!code.equals(currency.code)) return false;
        return Objects.equals(paymentSystem, currency.paymentSystem);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + (paymentSystem != null ? paymentSystem.hashCode() : 0);
        return result;
    }
}
