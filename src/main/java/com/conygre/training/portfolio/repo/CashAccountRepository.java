package com.conygre.training.portfolio.repo;

import com.conygre.training.portfolio.entities.CashAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashAccountRepository extends JpaRepository<CashAccount, Integer> {
}
