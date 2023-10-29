package ru.mnk.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mnk.core.domain.Currency;
import ru.mnk.core.domain.PaymentSystem;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCodeAndPaymentSystem(String code, PaymentSystem paymentSystem);
}
