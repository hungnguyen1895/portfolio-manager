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
        List<StockWithPercent> valuesList;
        try{
            symbols = marketDAO.getIndiciesList();
            valuesList = marketDAO.getMarketChangePercents(symbols);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        sortListBasedOnMarketChangePercent(valuesList);
        Collections.reverse(valuesList);
        return valuesList;

    }

    @Override
    public List<StockWithPercent> getBondMarketMovers() {
        List<String> symbols;
        List<StockWithPercent> valuesList;
        try{
            symbols = marketDAO.getBondsList();
            valuesList = marketDAO.getMarketChangePercents(symbols);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        sortListBasedOnMarketChangePercent(valuesList);
        Collections.reverse(valuesList);
        return valuesList;
    }


    public static void sortListBasedOnMarketChangePercent(List<StockWithPercent> valuesList){
        if(valuesList == null)
            return;


        for (int i = 0; i < valuesList.size(); i++) {
            for (int j = 1; j < (valuesList.size() - i); j++) {
                if (valuesList.get(j - 1).getPercentChange() > valuesList.get(j).getPercentChange()) {
                    //swap elements
                    StockWithPercent temp = valuesList.get(j - 1);
                    valuesList.set(j - 1, valuesList.get(j)) ;
                    valuesList.set(j,temp) ;
                }

            }
        }
    }

}
