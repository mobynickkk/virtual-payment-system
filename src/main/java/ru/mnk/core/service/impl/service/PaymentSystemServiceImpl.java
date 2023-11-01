package ru.mnk.core.service.impl.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.mnk.core.exceptions.NotFoundException;
import ru.mnk.core.service.api.service.PaymentSystemService;
import ru.mnk.domain.entity.PaymentSystem;
import ru.mnk.domain.repository.PaymentSystemRepository;

@Service
@AllArgsConstructor
public class PaymentSystemServiceImpl implements PaymentSystemService {
    private final PaymentSystemRepository paymentSystemRepository;

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
}
