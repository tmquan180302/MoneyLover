package com.poly.moneylover.models.ReportModels;

import com.poly.moneylover.models.Transaction;

import java.io.Serializable;
import java.util.List;

public class DetailReportDTO implements Serializable {
    private List<Chart> chart;
    private List<Transaction> transactions;

    public List<Chart> getChart() {
        return chart;
    }

    public void setChart(List<Chart> chart) {
        this.chart = chart;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
