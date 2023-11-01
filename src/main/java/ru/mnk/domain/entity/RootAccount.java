package ru.mnk.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("root")
public class RootAccount extends Account {

    public RootAccount() {}

    public RootAccount(Account account) {
        this.setId(account.getId());
        this.setPaymentSystem(account.getPaymentSystem());
        this.setStatus(account.getStatus());
    }

    @Override
    public Boolean isRoot() {
        return true;
    }
}
