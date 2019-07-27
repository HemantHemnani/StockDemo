package com.trackstockapp.monthlydata;

public class MonthlyStockBean {

    public String open;
    public String close;
    public String high;
    public String low;
    public String volume;

    public MonthlyStockBean(String open, String close, String high, String low, String volume) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }
}
