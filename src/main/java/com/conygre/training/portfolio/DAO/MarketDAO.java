package com.conygre.training.portfolio.DAO;

import com.conygre.training.portfolio.pojo.StockWithPercent;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface MarketDAO {
     List<StockWithPercent> getMarketChangePercents(List<String> symbols) throws ParseException, IOException;
     List<String> getIndiciesList() throws ParseException, IOException;
     List<String> getBondsList();
     Double getMarketPrice(String symbol) throws IOException, ParseException;
     Double getSymbolWeekChange(String symbol) throws IOException, ParseException;

}
