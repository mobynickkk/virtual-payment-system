package ru.mnk.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mnk.domain.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
