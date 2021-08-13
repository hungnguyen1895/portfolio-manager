package com.conygre.training.portfolio.rest;


import com.conygre.training.portfolio.service.CashAccountService;
import com.conygre.training.portfolio.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/market")
public class MarketController {


    @Autowired
    private MarketService marketService;

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public List<String> getIndexMarketMovers() {
        return marketService.getIndexMarketMovers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bond")
    public List<String> getBondMarketMovers() {
        return marketService.getBondMarketMovers();
    }

}
