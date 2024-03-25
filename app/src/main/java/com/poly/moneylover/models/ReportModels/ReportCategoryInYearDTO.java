package com.poly.moneylover.models.ReportModels;


import java.util.List;

public class ReportCategoryInYearDTO {
    private int expense;
    private int revenue;
    private int total;
    private List<ReportCategoryDTO> category;

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ReportCategoryDTO> getCategory() {
        return category;
    }

    public void setCategory(List<ReportCategoryDTO> category) {
        this.category = category;
    }
}
