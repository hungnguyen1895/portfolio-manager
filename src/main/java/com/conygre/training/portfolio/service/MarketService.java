package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.pojo.StockWithPercent;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface MarketService {


    public List<StockWithPercent>  getIndexMarketMovers();
    public List<StockWithPercent>  getBondMarketMovers();
}
