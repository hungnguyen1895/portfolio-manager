package com.conygre.training.portfolio.rest;


import com.conygre.training.portfolio.pojo.StockWithPercent;
import com.conygre.training.portfolio.service.CashAccountService;
import com.conygre.training.portfolio.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/market")
public class MarketController {


    @Autowired
    private MarketService marketService;

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public List<StockWithPercent> getIndexMarketMovers() {
        return marketService.getIndexMarketMovers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bond")
    public List<StockWithPercent> getBondMarketMovers() {
        return marketService.getBondMarketMovers();
    }

}
