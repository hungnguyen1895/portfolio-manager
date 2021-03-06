package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.DAO.MarketDAO;
import com.conygre.training.portfolio.entities.Investment;
import com.conygre.training.portfolio.pojo.StockHistoricalData;
import com.conygre.training.portfolio.repo.InvestmentRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InvestmentServiceImpl implements InvestmentService{
    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private MarketDAO marketDAO;

    @Override
    public Collection<Investment> getAllInvestments(Integer id) {
        return investmentRepository.findByUserId(id);
    }

    @Override
    public Double getTotalInvestment(Integer userID) throws IOException, ParseException {
        Collection<Investment> investments = getAllInvestments(userID);
        double totalInvestment = 0.0;
        for (Investment investment: investments)
            // currentPrice =  call API to get price for investment.getStockSymbol(
            totalInvestment += investment.getQuantity() * marketDAO.getMarketPrice(investment.getStockSymbol());

        return totalInvestment;
    }

    @Override
    public Double getStockChange(String timePeriod, Integer userID) throws IOException, ParseException {
        double change = 0.0;
        HashMap<String, Double> symbols = getAllInvestmentSymbols(userID);
        for (String symbol : symbols.keySet())
            change += marketDAO.getSymbolTimeChange(symbol, timePeriod) * symbols.get(symbol);

        return change;
    }

    @Override
    public StockHistoricalData getHistoricalData(String stockID) throws IOException, ParseException {
        return marketDAO.getStockHistoricalData(stockID);
    }


    private HashMap<String, Double> getAllInvestmentSymbols(Integer userID) {
        Collection<Investment> investments = getAllInvestments(userID);

        HashMap<String, Double> symbols = new HashMap<>();

        for (Investment investment: investments) {
            symbols.put(investment.getStockSymbol(), investment.getQuantity());
        }

        return symbols;
    }
}
