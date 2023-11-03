package ru.mnk.core.service.impl.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mnk.core.service.api.AccountService;
import ru.mnk.domain.transients.Balance;
import ru.mnk.core.service.api.PaymentSystemService;
import ru.mnk.core.service.api.facade.AccountFacade;
import ru.mnk.domain.entity.Account;
import ru.mnk.io.converter.BalanceConverter;
import ru.mnk.io.dto.BalanceDto;

@Service
@AllArgsConstructor
public class AccountFacadeImpl implements AccountFacade {
    private final AccountService accountService;
    private final PaymentSystemService paymentSystemService;
    private final BalanceConverter balanceConverter;

    @Override
    @Transactional
    public BalanceDto getBalance(Long id) {
        Account account = accountService.getAccount(id);
        Balance balance = accountService.getBalance(account);
        return balanceConverter.convert(balance);
    }

    @Override
    public Account createAccount(Long paymentSystemId) {
        return accountService.createAccount(paymentSystemService.getPaymentSystem(paymentSystemId));
    }
}
