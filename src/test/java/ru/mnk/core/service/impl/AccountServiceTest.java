package ru.mnk.core.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.Payment;
import ru.mnk.core.service.api.CurrencyService;
import ru.mnk.domain.repository.AccountRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.mnk.domain.entity.Status.DONE;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    private static final Currency RUB = new Currency();
    private static final Currency USD = new Currency();
    private static final BigDecimal R4D = new BigDecimal(92);

    private static final CurrencyService currencyService = mock(CurrencyService.class);
    private static final AccountRepository accountRepository = mock(AccountRepository.class);
    @InjectMocks
    private final AccountServiceImpl accountService = new AccountServiceImpl(accountRepository);

    @BeforeAll
    public static void before() {
        RUB.setCode("RUB");
        USD.setCode("USD");

        when(currencyService.getExchangeRate(RUB, USD)).thenReturn(BigDecimal.ONE.divide(R4D, new MathContext(4)));
        when(currencyService.getExchangeRate(USD, RUB)).thenReturn(R4D);
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void testBalance(Account account) {
        assertEquals(0,
                R4D.subtract(accountService.getBalance(account).getBalance(RUB, currencyService)).signum());
    }

    static Stream<Account> argsProviderFactory() {
        Account account1 = new Account();
        Account account2 = new Account();
        Account account3 = new Account();

        account1.getReceivedPayments().add(new Payment() {{setAmount(R4D);setCurrency(RUB);setStatus(DONE);}});
        account2.getReceivedPayments().add(new Payment() {{setAmount(BigDecimal.ONE);setCurrency(USD);setStatus(DONE);}});
        account3.getReceivedPayments()
                .add(new Payment() {{
                    setAmount(R4D.divide(new BigDecimal(2), RoundingMode.CEILING));
                    setCurrency(RUB);setStatus(DONE);}});
        account3.getReceivedPayments()
                .add(new Payment() {{
                    setAmount(BigDecimal.ONE.divide(new BigDecimal(2), new MathContext(2)));
                    setCurrency(USD);setStatus(DONE);}});

        return Stream.of(account1, account2, account3);
    }
}
