package com.conygre.training.portfolio.DAO;

import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface MarketDAO {
    public HashMap<String, Double> getMarketChangePercents(List<String> symbols) throws ParseException, IOException;
    public List<String> getIndiciesList() throws ParseException, IOException;
    public List<String> getBondsList();
    public Double getMarketPrice(String symbol) throws IOException, ParseException;
    public Double getSymbolWeekChange(String symbol) throws IOException, ParseException;

}
