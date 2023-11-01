package ru.mnk.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.core.exceptions.NotFoundException;
import ru.mnk.core.service.api.AccountService;
import ru.mnk.core.service.api.PaymentSystemService;
import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.PaymentSystem;
import ru.mnk.domain.entity.RootAccount;
import ru.mnk.domain.repository.PaymentSystemRepository;

@Service
@AllArgsConstructor
public class PaymentSystemServiceImpl implements PaymentSystemService {
    private final PaymentSystemRepository paymentSystemRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public PaymentSystem createNewPaymentSystem() {
        return paymentSystemRepository.save(new PaymentSystem());
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public PaymentSystem getPaymentSystem(Long id) {
        return paymentSystemRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void deletePaymentSystem(Long id) {
        paymentSystemRepository.deleteById(id);
    }

    @Override
    public RootAccount setRootAccount(Long id, Account account) {
        return accountService.convertToRootAccount(account);
    }

    @Override
    public void deleteRootAccount(Long id) {
        paymentSystemRepository.findById(id)
                .ifPresent(paymentSystem -> accountService.deleteAccount(paymentSystem.getRootAccount()));
    }
}
