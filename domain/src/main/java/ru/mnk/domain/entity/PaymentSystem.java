package ru.mnk.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "payment_systems")
public class PaymentSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps_seq")
    @SequenceGenerator(name = "ps_seq", sequenceName = "ps_seq", allocationSize=1)
    private Long id;

    @OneToOne(mappedBy = "paymentSystem")
    private RootAccount rootAccount;

    @OneToMany(mappedBy = "paymentSystem", fetch = FetchType.LAZY)
    private Set<Account> accounts;

    @OneToMany(mappedBy = "paymentSystem")
    private Set<Currency> currencies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentSystem that = (PaymentSystem) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(rootAccount, that.rootAccount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rootAccount != null ? rootAccount.hashCode() : 0);
        return result;
    }
}
