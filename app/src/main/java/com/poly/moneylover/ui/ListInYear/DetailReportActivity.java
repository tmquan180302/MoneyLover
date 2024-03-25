package com.poly.moneylover.ui.ListInYear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.poly.moneylover.R;
import com.poly.moneylover.databinding.ActivityDetailReportBinding;
import com.poly.moneylover.models.ReportModels.Chart;
import com.poly.moneylover.models.ReportModels.DetailReportDTO;
import com.poly.moneylover.models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailReportActivity extends AppCompatActivity {
    private ActivityDetailReportBinding binding;
    private DetailReportDTO list ;
    private int sumTotal = 0;
    private TextView[] monthTextViews = new TextView[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeMonthTextViews();
        inintData();
        setViewClick();
    }
    public void setViewClick(){
        binding.imgBack.setOnClickListener(v -> {
            finish();
        });
    }
    private void inintData(){
        String title = (String) getIntent().getSerializableExtra(ChiFragment.DATA_TITLE);
        list = (DetailReportDTO) getIntent().getSerializableExtra(ChiFragment.DATA_DETAIL_REPORT);
        binding.tvTitle.setText(title);
        setViewDataChart(list.getChart());
        setViewData(list.getChart());
    }

    public void setViewDataChart(List<Chart> list) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            for (int i = 1; i <= 12; i++) {
                entries.add(new BarEntry(i, 0f));
            }
        } else {
            Collections.sort(list, (o1, o2) -> Integer.compare(o1.getMonth(), o2.getMonth()));

            int index = 0;
            for (int i = 1; i <= 12; i++) {
                if (index < list.size() && list.get(index).getMonth() == i) {
                    entries.add(new BarEntry(i, list.get(index).getTotal()));
                    sumTotal += list.get(index).getTotal();
                    index++;
                } else {
                    entries.add(new BarEntry(i, 0f));
                }
            }
        }
        binding.tvSum.setText(String.valueOf(sumTotal) + "đ");

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColor(Color.parseColor("#00BFFF"));

        BarData barData = new BarData(dataSet);

        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(entries.size());

        YAxis yAxisLeft = binding.barChart.getAxisLeft();
        yAxisLeft.setGranularity(500f);
        yAxisLeft.setAxisMinimum(0f);

        YAxis yAxisRight = binding.barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        binding.barChart.setData(barData);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.setFitBars(true);
        binding.barChart.invalidate();
    }

    private void initializeMonthTextViews() {
        monthTextViews[0] = binding.tvThang1;
        monthTextViews[1] = binding.tvThang2;
        monthTextViews[2] = binding.tvThang3;
        monthTextViews[3] = binding.tvThang4;
        monthTextViews[4] = binding.tvThang5;
        monthTextViews[5] = binding.tvThang6;
        monthTextViews[6] = binding.tvThang7;
        monthTextViews[7] = binding.tvThang8;
        monthTextViews[8] = binding.tvThang9;
        monthTextViews[9] = binding.tvThang10;
        monthTextViews[10] = binding.tvThang11;
        monthTextViews[11] = binding.tvThang12;
    }

    public void setViewData(List<Chart> list) {
        if (list != null && monthTextViews != null) {
            for (Chart budgetDTO : list) {
                int month = budgetDTO.getMonth();
                int total = budgetDTO.getTotal();

                if (month >= 1 && month <= 12) {
                    monthTextViews[month - 1].setText(String.valueOf(total) + "đ");
                } else {
                    monthTextViews[month - 1].setText("0đ");
                }
            }
        }
    }
}