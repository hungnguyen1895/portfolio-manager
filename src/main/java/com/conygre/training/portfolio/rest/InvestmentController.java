package com.conygre.training.portfolio.rest;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.service.CashAccountService;
import com.conygre.training.portfolio.service.InvestmentService;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/investments")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;

    @GetMapping
    public Double getInvestment() throws IOException, ParseException {
        return investmentService.getTotalInvestment();
    }

    @GetMapping("/weekly")
    public Double getWeekChange() throws IOException, ParseException {
        return investmentService.getWeekChange();
    }
}
