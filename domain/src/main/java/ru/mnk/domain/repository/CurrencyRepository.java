package ru.mnk.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mnk.domain.entity.Currency;
import ru.mnk.domain.entity.PaymentSystem;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCodeAndPaymentSystem(String code, PaymentSystem paymentSystem);
}
