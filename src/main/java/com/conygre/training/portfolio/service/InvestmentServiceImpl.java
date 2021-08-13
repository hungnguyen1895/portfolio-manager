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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Override
    public Double getWeekChange() throws IOException, ParseException {
        Double change = 0.0;

        HashMap<String, Double> symbols = getAllInvestmentSymbols();

        for (String symbol: symbols.keySet()) {
            Request request = new Request.Builder()
                    .url("https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + symbol +"&outputsize=compact&datatype=json")
                    .get()
                    .addHeader("x-rapidapi-key", "fad70b116amshdf5fa2b2013d40bp157fa6jsn559c34c2e61e")
                    .addHeader("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
            jsonObject = (JSONObject)jsonObject.get("Time Series (Daily)");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            Date today = new Date(date.getTime() - (1 * DAY_IN_MS));
            Date lastweek = new Date(date.getTime() - (8 * DAY_IN_MS));
            String todayString = dateFormat.format(today).toString();
            String lastweekString = dateFormat.format(lastweek).toString();
            JSONObject todayObject = (JSONObject)jsonObject.get(todayString);
            JSONObject lastweekObject = (JSONObject)jsonObject.get(lastweekString);

            double currentChange = Double.parseDouble(todayObject.get("4. close").toString()) - Double.parseDouble(lastweekObject.get("4. close").toString());
            double share = symbols.get(symbol);
            change += currentChange * share;
        }

        return change;
    }

    private HashMap<String, Double> getAllInvestmentSymbols() {
        Collection<Investment> investments = getAllInvestments();

        HashMap<String, Double> symbols = new HashMap<>();

        for (Investment investment: investments) {
            symbols.put(investment.getStockSymbol(), investment.getQuantity());
        }

        return symbols;
    }
}
