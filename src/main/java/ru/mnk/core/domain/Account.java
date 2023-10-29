package ru.mnk.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "accounts")
@DiscriminatorColumn(name="account_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("avg")
public class Account {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<Payment> sentPayments;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private Set<Payment> receivedPayments;

    @ManyToOne
    @JoinColumn(name = "paymentSystemId", nullable = false)
    private PaymentSystem paymentSystem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!Objects.equals(id, account.id)) return false;
        return status == account.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
