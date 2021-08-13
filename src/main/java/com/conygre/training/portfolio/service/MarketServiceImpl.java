package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.DAO.MarketDAO;
import com.conygre.training.portfolio.pojo.StockWithPercent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class MarketServiceImpl implements MarketService{

    @Autowired
    private MarketDAO marketDAO;

    @Override
    public List<StockWithPercent> getIndexMarketMovers() {
        List<String> symbols;
        HashMap<String, Double> valuesMap;
        try{
            symbols = marketDAO.getIndiciesList();
            valuesMap = marketDAO.getMarketChangePercents(symbols);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        sortListBasedOnMarketChangePercent(symbols, valuesMap);
        Collections.reverse(symbols);
        return convertStockAndMapToObject(symbols, valuesMap);

    }

    @Override
    public List<StockWithPercent>  getBondMarketMovers() {
        List<String> symbols;
        HashMap<String, Double> valuesMap;
        try{
            symbols = marketDAO.getBondsList();
            valuesMap = marketDAO.getMarketChangePercents(symbols);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        sortListBasedOnMarketChangePercent(symbols, valuesMap);
        Collections.reverse(symbols);
        return convertStockAndMapToObject(symbols, valuesMap);
    }


    public static void sortListBasedOnMarketChangePercent(List<String> symbols, HashMap<String, Double> valuesMap){
        for (int i = 0; i < symbols.size(); i++) {
            for (int j = 1; j < (symbols.size() - i); j++) {
                if (valuesMap.get(symbols.get(j - 1)) > valuesMap.get(symbols.get(j))) {
                    //swap elements
                    String temp = symbols.get(j - 1);
                    symbols.set(j - 1, symbols.get(j)) ;
                    symbols.set(j,temp) ;
                }

            }
        }
    }

    public static List<StockWithPercent> convertStockAndMapToObject(List<String> symbols, HashMap<String, Double> valuesMap){
        List<StockWithPercent> stocks = new LinkedList<>();
        try{
            for(String symbol : symbols){
                stocks.add(new StockWithPercent(symbol, valuesMap.get(symbol)));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return stocks;
    }
}
