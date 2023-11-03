package ru.mnk.core.service.impl.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.mnk.core.exceptions.ValidationException;
import ru.mnk.core.service.api.AccountService;
import ru.mnk.core.service.api.PaymentSystemService;
import ru.mnk.core.service.api.facade.PaymentSystemFacade;
import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.PaymentSystem;
import ru.mnk.domain.entity.RootAccount;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentSystemFacadeImpl implements PaymentSystemFacade {
    private final PaymentSystemService paymentSystemService;
    private final AccountService accountService;

    @Override
    @Transactional
    public PaymentSystem createPaymentSystem() {
        return paymentSystemService.createNewPaymentSystem();
    }

    @Override
    @Transactional
    public RootAccount getRootAccount(Long id) {
        return paymentSystemService.getPaymentSystem(id).getRootAccount();
    }

    @Override
    @Transactional
    public void setRootAccount(Long paymentSystemId, Long accountId) throws ValidationException {
        PaymentSystem paymentSystem = paymentSystemService.getPaymentSystem(paymentSystemId);
        Long rootId = Optional.ofNullable(paymentSystem.getRootAccount())
                .map(RootAccount::getId).orElse(0L);
        if (rootId != 0L) {
            paymentSystemService.deleteRootAccount(paymentSystemId);
        }
        Account account = accountService.getAccount(accountId);
        if (!account.getPaymentSystem().equals(paymentSystem)) {
            throw new ValidationException("different.paymentSystems");
        }
        RootAccount rootAccount = accountService.convertToRootAccount(account);
        paymentSystemService.setRootAccount(paymentSystemId, rootAccount);
    }
}
