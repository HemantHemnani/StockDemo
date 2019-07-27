package com.trackstockapp.dashboard;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StockSearchBean implements Parcelable {



        @SerializedName("symbols_requested")
        @Expose
        private Integer symbolsRequested;
        @SerializedName("symbols_returned")
        @Expose
        private Integer symbolsReturned;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

    protected StockSearchBean(Parcel in) {
        if (in.readByte() == 0) {
            symbolsRequested = null;
        } else {
            symbolsRequested = in.readInt();
        }
        if (in.readByte() == 0) {
            symbolsReturned = null;
        } else {
            symbolsReturned = in.readInt();
        }
    }

    public static final Creator<StockSearchBean> CREATOR = new Creator<StockSearchBean>() {
        @Override
        public StockSearchBean createFromParcel(Parcel in) {
            return new StockSearchBean(in);
        }

        @Override
        public StockSearchBean[] newArray(int size) {
            return new StockSearchBean[size];
        }
    };

    public Integer getSymbolsRequested() {
            return symbolsRequested;
        }

        public void setSymbolsRequested(Integer symbolsRequested) {
            this.symbolsRequested = symbolsRequested;
        }

        public Integer getSymbolsReturned() {
            return symbolsReturned;
        }

        public void setSymbolsReturned(Integer symbolsReturned) {
            this.symbolsReturned = symbolsReturned;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (symbolsRequested == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(symbolsRequested);
        }
        if (symbolsReturned == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(symbolsReturned);
        }
    }

    public static class Datum implements Parcelable{

    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_open")
    @Expose
    private String priceOpen;
    @SerializedName("day_high")
    @Expose
    private String dayHigh;
    @SerializedName("day_low")
    @Expose
    private String dayLow;
    @SerializedName("52_week_high")
    @Expose
    private String _52WeekHigh;
    @SerializedName("52_week_low")
    @Expose
    private String _52WeekLow;
    @SerializedName("day_change")
    @Expose
    private String dayChange;
    @SerializedName("change_pct")
    @Expose
    private String changePct;
    @SerializedName("close_yesterday")
    @Expose
    private String closeYesterday;
    @SerializedName("market_cap")
    @Expose
    private String marketCap;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("volume_avg")
    @Expose
    private String volumeAvg;
    @SerializedName("shares")
    @Expose
    private String shares;
    @SerializedName("stock_exchange_long")
    @Expose
    private String stockExchangeLong;
    @SerializedName("stock_exchange_short")
    @Expose
    private String stockExchangeShort;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("timezone_name")
    @Expose
    private String timezoneName;
    @SerializedName("gmt_offset")
    @Expose
    private String gmtOffset;
    @SerializedName("last_trade_time")
    @Expose
    private String lastTradeTime;

        protected Datum(Parcel in) {
            symbol = in.readString();
            name = in.readString();
            currency = in.readString();
            price = in.readString();
            priceOpen = in.readString();
            dayHigh = in.readString();
            dayLow = in.readString();
            _52WeekHigh = in.readString();
            _52WeekLow = in.readString();
            dayChange = in.readString();
            changePct = in.readString();
            closeYesterday = in.readString();
            marketCap = in.readString();
            volume = in.readString();
            volumeAvg = in.readString();
            shares = in.readString();
            stockExchangeLong = in.readString();
            stockExchangeShort = in.readString();
            timezone = in.readString();
            timezoneName = in.readString();
            gmtOffset = in.readString();
            lastTradeTime = in.readString();
        }

        public static final Creator<Datum> CREATOR = new Creator<Datum>() {
            @Override
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            @Override
            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        };

        public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(String priceOpen) {
        this.priceOpen = priceOpen;
    }

    public String getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(String dayHigh) {
        this.dayHigh = dayHigh;
    }

    public String getDayLow() {
        return dayLow;
    }

    public void setDayLow(String dayLow) {
        this.dayLow = dayLow;
    }

    public String get52WeekHigh() {
        return _52WeekHigh;
    }

    public void set52WeekHigh(String _52WeekHigh) {
        this._52WeekHigh = _52WeekHigh;
    }

    public String get52WeekLow() {
        return _52WeekLow;
    }

    public void set52WeekLow(String _52WeekLow) {
        this._52WeekLow = _52WeekLow;
    }

    public String getDayChange() {
        return dayChange;
    }

    public void setDayChange(String dayChange) {
        this.dayChange = dayChange;
    }

    public String getChangePct() {
        return changePct;
    }

    public void setChangePct(String changePct) {
        this.changePct = changePct;
    }

    public String getCloseYesterday() {
        return closeYesterday;
    }

    public void setCloseYesterday(String closeYesterday) {
        this.closeYesterday = closeYesterday;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolumeAvg() {
        return volumeAvg;
    }

    public void setVolumeAvg(String volumeAvg) {
        this.volumeAvg = volumeAvg;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getStockExchangeLong() {
        return stockExchangeLong;
    }

    public void setStockExchangeLong(String stockExchangeLong) {
        this.stockExchangeLong = stockExchangeLong;
    }

    public String getStockExchangeShort() {
        return stockExchangeShort;
    }

    public void setStockExchangeShort(String stockExchangeShort) {
        this.stockExchangeShort = stockExchangeShort;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    public String getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(String gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(symbol);
            parcel.writeString(name);
            parcel.writeString(currency);
            parcel.writeString(price);
            parcel.writeString(priceOpen);
            parcel.writeString(dayHigh);
            parcel.writeString(dayLow);
            parcel.writeString(_52WeekHigh);
            parcel.writeString(_52WeekLow);
            parcel.writeString(dayChange);
            parcel.writeString(changePct);
            parcel.writeString(closeYesterday);
            parcel.writeString(marketCap);
            parcel.writeString(volume);
            parcel.writeString(volumeAvg);
            parcel.writeString(shares);
            parcel.writeString(stockExchangeLong);
            parcel.writeString(stockExchangeShort);
            parcel.writeString(timezone);
            parcel.writeString(timezoneName);
            parcel.writeString(gmtOffset);
            parcel.writeString(lastTradeTime);
        }
    }


}