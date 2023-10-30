package ru.mnk.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mnk.core.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
