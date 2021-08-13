package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.Investment;
import com.conygre.training.portfolio.repo.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class InvestmentServiceImpl implements InvestmentService{
    @Autowired
    private InvestmentRepository investmentRepository;

    @Override
    public Collection<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    // getInvestment change by week, month, last quarter, year to date (YTD)

    // TotalValueChange = 0;
    // get list of all stocks
        // A = get price of last week * quantity of a stock: 500
        // B = get current price * quantity of stock: 1000
        // TotalValueChange += B - A;
}
