package com.conygre.training.portfolio.pojo;

public class StockWithPercent {
    private String stockName;
    private double percentChange;

    public StockWithPercent(String stockName, double percentChange) {
        this.stockName = stockName;
        this.percentChange = percentChange;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }
}
