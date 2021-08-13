package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.CashAccount;
import com.conygre.training.portfolio.entities.Investment;

import java.util.Collection;

public interface InvestmentService {
    Collection<Investment> getAllInvestments();
}
