package com.poly.moneylover.ui.transaction;

public class SumPriceAndDate {
    public long sumPriceType0;
    public String dateKey;

    public SumPriceAndDate(long sumPriceType0, String dateKey) {
        this.sumPriceType0 = sumPriceType0;
        this.dateKey = dateKey;
    }

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }


}