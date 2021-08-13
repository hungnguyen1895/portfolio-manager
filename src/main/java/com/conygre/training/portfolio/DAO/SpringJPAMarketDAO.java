package com.conygre.training.portfolio.DAO;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SpringJPAMarketDAO implements MarketDAO{

    @Override
    public HashMap<String, Double> getMarketChangePercents(List<String> symbols) throws ParseException, IOException {

        if(symbols.size() ==0)
            return null;

        OkHttpClient client = new OkHttpClient();
        StringBuilder symbolString = new StringBuilder(symbols.get(0));
        for(int i=1; i <symbols.size();i++){
            symbolString.append("%2C").append(symbols.get(i));
        }
        HashMap<String, Double> changePercentMap = new HashMap<>();

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
            changePercentMap.put(symbols.get(i), changePercent);
        }

        return changePercentMap;
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
}
