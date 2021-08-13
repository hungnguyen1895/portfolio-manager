package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.Investment;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvestmentServiceImpl implements InvestmentService{
    @Autowired
    private InvestmentRepository investmentRepository;

    @Override
    public Collection<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    @Override
    public Double getTotalInvestment() throws IOException, ParseException {
        Collection<Investment> investments = getAllInvestments();
        double totalInvestment = 0.0;
        for (Investment investment: investments)
            // currentPrice =  call API to get price for investment.getStockSymbol(
            totalInvestment += investment.getQuantity() * getMarketPrice(investment.getStockSymbol());

        return totalInvestment;
    }


    private Double getMarketPrice(String symbol) throws IOException, ParseException {
        Request request = new Request.Builder()
                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=" + symbol)
                .get()
                .addHeader("x-rapidapi-key", "f7dda5a1famsh26f811330bca0f1p1389e3jsn68144518d2cf")
                .addHeader("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
        jsonObject = (JSONObject)jsonObject.get("quoteResponse");
        JSONArray jsonArray = (JSONArray)jsonObject.get("result");
        jsonObject = (JSONObject)jsonArray.get(0);
        Double marketPrice = (Double)jsonObject.get("regularMarketPrice");
        return marketPrice;
    }

//    @Override
//    public Double getWeekChange() throws IOException, ParseException {
////        String url = "http://rapidapi.com";
////
////        Map<String, String> urlParams = new HashMap<>();
////        urlParams.put("symbol", "TSLA");
////        urlParams.put("function", "TIME_SERIES_DAILY_ADJUSTED");
////        RestTemplate restTemplate = new RestTemplate();
////        System.out.println(restTemplate.getForObject(url, String.class, urlParams));
////        System.out.println("testing");
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=TSLA&outputsize=compact&datatype=json")
//                .get()
//                .addHeader("x-rapidapi-key", "fad70b116amshdf5fa2b2013d40bp157fa6jsn559c34c2e61e")
//                .addHeader("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
////        JSONParser g = new JSONParser();
////        JSONObject json = (JSONObject) g.parse(response.body().string());
//
//
//        System.out.println(response.body().string());
////        System.out.println(response.)
//        return 0.0;
//    }

    // getInvestment change by week, month, last quarter, year to date (YTD)

    // TotalValueChange = 0;
    // get list of all stocks
        // A = get price of last week * quantity of a stock: 500
        // B = get current price * quantity of stock: 1000
        // TotalValueChange += B - A;

}
