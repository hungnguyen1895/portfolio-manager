package com.conygre.training.portfolio.DAO;

import com.conygre.training.portfolio.pojo.StockHistoricalData;
import com.conygre.training.portfolio.pojo.StockPriceData;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class SpringJPAMarketDAO implements MarketDAO{
    //TODO UPDATE TO NEW PROFILE ON DEMO DAY
    private final String hostValue = "apidojo-yahoo-finance-v1.p.rapidapi.com";
    private final String keyValue = "db6eb71ecdmsh807241375c5cf11p16acf4jsnbfa1fd68a129";

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
                .addHeader("x-rapidapi-host", hostValue)
                .addHeader("x-rapidapi-key", keyValue)
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();

        if(body.contains("exceeded the MONTHLY quota")){
            System.out.println("Used maximum API calls possible this month");
            return null;
        }

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
                .addHeader("x-rapidapi-host", hostValue)
                .addHeader("x-rapidapi-key", keyValue)
                .build();

        String body = client.newCall(request).execute().body().string();


        if(body.contains("exceeded the MONTHLY quota")){
            System.out.println("Used maximum API calls possible this month");
            return null;
        }

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
                .addHeader("x-rapidapi-host", hostValue)
                .addHeader("x-rapidapi-key", keyValue)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String body = response.body().string();

        if(body.contains("exceeded the MONTHLY quota")){
            System.out.println("Used maximum API calls possible this month");
            return 0.0;
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
        jsonObject = (JSONObject)jsonObject.get("quoteResponse");
        JSONArray jsonArray = (JSONArray)jsonObject.get("result");
        jsonObject = (JSONObject)jsonArray.get(0);
        Double marketPrice = (Double)jsonObject.get("regularMarketPrice");
        return marketPrice;
    }

    @Override
    public Double getSymbolTimeChange(String symbol, String timePeriod) throws IOException, ParseException {
        Request         request = new Request.Builder()
                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?symbol=" + symbol + "&region=US")
                .get()
                .addHeader("x-rapidapi-host", hostValue)
                .addHeader("x-rapidapi-key", keyValue)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String body = response.body().string();

        if(body.contains("exceeded the MONTHLY quota")){
            System.out.println("Used maximum API calls possible this month");
            return 0.0;
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonRequestBody = (JSONObject) jsonParser.parse(body);
        JSONArray priceArray = (JSONArray)jsonRequestBody.get("prices");

        if(priceArray == null){
            //TODO throw an exception here to handle in calling methods


            return 0.0;

        }

        double priceDifference = 0.0;
        JSONObject currentDayObject = (JSONObject) priceArray.get(0);
        long timeStampCurrDay = ((long)(int)currentDayObject.get("date")) * 1000; // convert seconds to milliseconds for date conversion
        switch (timePeriod){

            case "month":
                Calendar monthCalendar = Calendar.getInstance();
                monthCalendar.setTimeInMillis(timeStampCurrDay);
                monthCalendar.add(Calendar.MONTH, -1);
                Date prevMonthDate = monthCalendar.getTime();

                //start 20 elements back to start at 4 weeks ago in your search
                for(int i = 20; i < priceArray.size(); i++){
                    JSONObject temp = (JSONObject) priceArray.get(i);
                    long timeStampNum = ((long)(int)temp.get("date")) * 1000; // convert seconds to milliseconds for date conversion
                    Calendar tempCalendar = Calendar.getInstance();
                    tempCalendar.setTimeInMillis(timeStampNum);
                    Date tempDate = tempCalendar.getTime();

                    if(tempDate.before(prevMonthDate)){
                        priceDifference = (double)currentDayObject.get("close") - (double)temp.get("close");
                        break;
                    }

                }
                break;
            case "quarter":
                Calendar quarterCalendar = Calendar.getInstance();
                quarterCalendar.setTimeInMillis(timeStampCurrDay);
                quarterCalendar.add(Calendar.MONTH, -3);
                Date prevQuarterDate = quarterCalendar.getTime();

                //start 60 elements back to start at 12 weeks ago in your search
                for(int i = 60; i < priceArray.size(); i++){
                    JSONObject temp = (JSONObject) priceArray.get(i);
                    long timeStampNum = ((long)(int)temp.get("date")) * 1000; // convert seconds to milliseconds for date conversion
                    Calendar tempCalendar = Calendar.getInstance();
                    tempCalendar.setTimeInMillis(timeStampNum);
                    Date tempDate = tempCalendar.getTime();

                    if(tempDate.before(prevQuarterDate)){
                        priceDifference = (double)currentDayObject.get("close") - (double)temp.get("close");
                        break;
                    }

                }
                break;

            case "year":
                JSONObject lastYearPrice = (JSONObject) priceArray.get(priceArray.size()-1);
                priceDifference = (double)currentDayObject.get("close") - (double)lastYearPrice.get("close");
                break;
            case "week":
            default:
                //TODO send back error in default work
                JSONObject lastWeekPrice = (JSONObject) priceArray.get(5);
                priceDifference = (double)currentDayObject.get("close") - (double)lastWeekPrice.get("close");
                break;
        }

        return priceDifference;

    }


    public StockHistoricalData getStockHistoricalData(String symbol) throws IOException, ParseException {
        Request request = new Request.Builder()
                .url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?symbol=" + symbol + "&region=US")
                .get()
                .addHeader("x-rapidapi-host", hostValue)
                .addHeader("x-rapidapi-key", keyValue)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String body = response.body().string();

        if (body.contains("exceeded the MONTHLY quota")) {
            System.out.println("Used maximum API calls possible this month");
            return null;
        }

        StockHistoricalData stockData = new StockHistoricalData(symbol);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonRequestBody = (JSONObject) jsonParser.parse(body);
        JSONArray priceArray = (JSONArray) jsonRequestBody.get("prices");

        if (priceArray == null) {
            //TODO throw an exception here to handle in calling methods
            return null;
        }

        for(int i = 0; i < priceArray.size(); i++){
            JSONObject temp = (JSONObject) priceArray.get(i);
            long timeStampNum = ((long)(int)temp.get("date")) * 1000; // convert seconds to milliseconds for date conversion
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTimeInMillis(timeStampNum);
            Date tempDate = tempCalendar.getTime();
            Double open;
            Double high;
            Double low;
            Double close;
            Integer volume;
            Double adjustedClose;

            if(temp.getAsNumber("open") == null || temp.getAsNumber("high") ==null ||
                    temp.getAsNumber("low") ==null || temp.getAsNumber("close") ==null ||
                    temp.getAsNumber("volume") ==null || temp.getAsNumber("adjclose") == null){
                continue;
            }



            try { open = (double) temp.getAsNumber("open"); }
            catch(Exception e){open = (double)(int)temp.getAsNumber("open");}
            //
            try { high = (double) temp.getAsNumber("high"); }
            catch(Exception e){high = (double)(int)temp.getAsNumber("high");}

            try { low = (double) temp.getAsNumber("low"); }
            catch(Exception e){low = (double)(int)temp.getAsNumber("low");}

            try { close = (double) temp.getAsNumber("close"); }
            catch(Exception e){close = (double)(int)temp.getAsNumber("close");}

            volume = (int) temp.getAsNumber("volume");

            try { adjustedClose = (double) temp.getAsNumber("adjclose"); }
            catch(Exception e){adjustedClose = (double)(int)temp.getAsNumber("adjclose");}

//
//            high = (double) temp.getAsNumber("high");
//            low = (double) temp.getAsNumber("low");
//            close = (double) temp.getAsNumber("close");
//            volume = (int) temp.getAsNumber("volume");
//            adjustedClose = (double) temp.getAsNumber("adjclose");




            stockData.prices.add(new StockPriceData(tempDate, open,high,low,close,volume,adjustedClose));
        }

        return stockData;
    }

}
