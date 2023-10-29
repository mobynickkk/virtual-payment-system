package ru.mnk.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mnk.core.domain.Account;
import ru.mnk.core.domain.Currency;
import ru.mnk.core.domain.Payment;
import ru.mnk.core.domain.Status;

import java.util.Set;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Set<Payment> findAllByStatus(Status status);

    Set<Payment> findAllByCurrency(Currency currency);

    Set<Payment> findAllByReceiverAndCurrency(Account receiver, Currency currency);

    Set<Payment> findAllBySenderAndCurrency(Account sender, Currency currency);

    Set<Payment> findAllByReceiverAndCurrencyAndStatus(Account receiver, Currency currency, Status status);

    Set<Payment> findAllBySenderAndCurrencyAndStatus(Account sender, Currency currency, Status status);
}
