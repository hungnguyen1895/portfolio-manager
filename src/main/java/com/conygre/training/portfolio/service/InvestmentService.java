package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.CashAccount;
import com.conygre.training.portfolio.entities.Investment;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.Collection;

public interface InvestmentService {
    Collection<Investment> getAllInvestments(Integer id);
    Double getTotalInvestment(Integer userID) throws IOException, ParseException;
    Double getStockChange(String timePeriod, Integer userID) throws IOException, ParseException;

}
