package com.conygre.training.portfolio.repo;

import com.conygre.training.portfolio.entities.Investment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Integer> {
     Collection<Investment> findByUserId(Integer userID);
}
