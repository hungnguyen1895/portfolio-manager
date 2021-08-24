package com.conygre.training.portfolio.rest;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.service.CashAccountService;
import com.conygre.training.portfolio.service.InvestmentService;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping("/investments")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;

    @GetMapping("/{userID}")
    public Double getInvestment(@PathVariable("userID") Integer userID) throws IOException, ParseException {
        return investmentService.getTotalInvestment(userID);
    }

    @GetMapping("/{timeperiod}/{userID}")
    public Double getStockChange(@PathVariable("timeperiod") String timeperiod, @PathVariable("userID") Integer userID)
            throws IOException, ParseException {
        return investmentService.getStockChange(timeperiod, userID);
    }

}
