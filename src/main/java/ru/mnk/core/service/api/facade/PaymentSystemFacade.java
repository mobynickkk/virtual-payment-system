package ru.mnk.core.service.api.facade;

import ru.mnk.core.exceptions.ValidationException;
import ru.mnk.domain.entity.PaymentSystem;
import ru.mnk.domain.entity.RootAccount;

public interface PaymentSystemFacade {

    PaymentSystem createPaymentSystem();

    RootAccount getRootAccount(Long id);

    void setRootAccount(Long paymentSystemId, Long accountId) throws ValidationException;
}
