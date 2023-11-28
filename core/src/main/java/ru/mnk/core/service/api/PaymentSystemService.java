package ru.mnk.core.service.api;

import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.PaymentSystem;
import ru.mnk.domain.entity.RootAccount;

public interface PaymentSystemService {
    PaymentSystem createNewPaymentSystem();

    PaymentSystem getPaymentSystem(Long id);

    void deletePaymentSystem(Long id);

    RootAccount setRootAccount(Long id, Account account);

    void deleteRootAccount(Long id);
}
