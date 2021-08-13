package com.conygre.training.portfolio.service;

import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface MarketService {


    public List<String>  getIndexMarketMovers();
    public List<String>  getBondMarketMovers();
}
