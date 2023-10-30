package ru.mnk.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mnk.domain.entity.Account;
import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.Payment;
import ru.mnk.domain.entity.Status;

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
