package com.conygre.training.portfolio.repo;

import com.conygre.training.portfolio.entities.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Integer> {
}
