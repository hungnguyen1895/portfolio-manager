package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.CashAccount;
import com.conygre.training.portfolio.entities.Investment;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.Collection;

public interface InvestmentService {
    Collection<Investment> getAllInvestments();
    Double getTotalInvestment() throws IOException, ParseException;
    Double getWeekChange() throws IOException, ParseException;
}
