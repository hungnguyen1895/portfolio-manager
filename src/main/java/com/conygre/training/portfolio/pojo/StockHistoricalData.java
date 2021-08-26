package com.conygre.training.portfolio.pojo;

import java.util.LinkedList;
import java.util.List;

public class StockHistoricalData {

    public String stockCode;
    public List<StockPriceData> prices;

    public String getstockCode() {
        return stockCode;
    }

    public void setstockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public StockHistoricalData(String stockCode) {

        this.stockCode = stockCode;
        prices = new LinkedList<>();
    }
}
