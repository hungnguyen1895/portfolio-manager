package com.conygre.training.portfolio.DAO;

import com.conygre.training.portfolio.pojo.StockWithPercent;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SpringJPAMarketDAO implements MarketDAO{

    @Override
    public List<StockWithPercent> getMarketChangePercents(List<String> symbols) throws ParseException, IOException {

        if(symbols.size() ==0)
            return null;

        OkHttpClient client = new OkHttpClient();
        StringBuilder symbolString = new StringBuilder(symbols.get(0));
        for(int i=1; i <symbols.size();i++){
            symbolString.append("%2C").append(symbols.get(i));
        }
        List<StockWithPercent> changePercentList = new LinkedList<>();

        Request request = new Request.Builder()
                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=" + symbolString.toString())
                .get()
                .addHeader("x-rapidapi-key", "f7dda5a1famsh26f811330bca0f1p1389e3jsn68144518d2cf")
                .addHeader("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
        jsonObject = (JSONObject)jsonObject.get("quoteResponse");
        JSONArray jsonArray = (JSONArray)jsonObject.get("result");
        for(int i =0; i < jsonArray.size();i++){
            jsonObject = (JSONObject)jsonArray.get(i);
            Double changePercent = (Double)jsonObject.get("regularMarketChangePercent");
            changePercentList.add(new StockWithPercent(symbols.get(i), changePercent));
        }

        return changePercentList;
    }

    @Override
    public List<String> getIndiciesList() throws ParseException, IOException {
        OkHttpClient client = new OkHttpClient();
        List<String> indexList = new LinkedList<>();
        JSONParser jsonParser = new JSONParser();
        Request request = new Request.Builder()
                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-summary?region=US")
                .get()
                .addHeader("x-rapidapi-key", "f7dda5a1famsh26f811330bca0f1p1389e3jsn68144518d2cf")
                .addHeader("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build();

        String body = client.newCall(request).execute().body().string();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
        jsonObject = (JSONObject)jsonObject.get("marketSummaryAndSparkResponse");
        JSONArray jsonArray = (JSONArray)jsonObject.get("result");
        for(int i=0; i < jsonArray.size();i++){
            JSONObject temp = (JSONObject)jsonArray.get(i);
            indexList.add(temp.get("symbol").toString());
        }

        return indexList;
    }

    @Override
    public List<String> getBondsList() {
        List<String> bonds = new LinkedList<>();
        bonds.add("^IRX");
        bonds.add("^FVX");
        bonds.add("^TNX");
        bonds.add("^TYX");

        return bonds;
    }

    @Override
    public Double getMarketPrice(String symbol) throws IOException, ParseException {
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
    public Double getSymbolWeekChange(String symbol) throws IOException, ParseException {
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

        return Double.parseDouble(todayObject.get("4. close").toString()) - Double.parseDouble(lastweekObject.get("4. close").toString());
    }
}