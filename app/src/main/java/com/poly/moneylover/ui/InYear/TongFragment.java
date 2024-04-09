package com.poly.moneylover.ui.InYear;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.YearListener;
import com.poly.moneylover.models.ReportModels.ReportInYearDTO;
import com.poly.moneylover.models.ReportModels.Chart;
import com.poly.moneylover.network.ReportApi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;


public class TongFragment extends Fragment implements YearListener {
    private TextView tvSum;
    private TextView tvAverage;
    private TextView tvThu;
    private TextView tvChi;
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tong, container, false);
        barChart = view.findViewById(R.id.barChart);
        tvSum = view.findViewById(R.id.tvSum);
        tvAverage = view.findViewById(R.id.tvAverage);
        tvThu = view.findViewById(R.id.tvThu);
        tvChi = view.findViewById(R.id.tvChi);


        return view;
    }


    public void initData(List<Chart> list, int yearSelect) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            for (int i = 1; i <= 12; i++) {
                entries.add(new BarEntry(i, 0f));
            }
        } else {
            Collections.sort(list, (o1, o2) -> Integer.compare(o1.getMonth(), o2.getMonth()));

            int index = 0;
            for (int i = 1; i <= 12; i++) {
                if (index < list.size() && list.get(index).getMonth() == i && list.get(index).getYear() == yearSelect) {
                    entries.add(new BarEntry(i, list.get(index).getTotal()));
                    index++;
                } else {
                    entries.add(new BarEntry(i, 0f));
                }
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Tổng thu chi");
        dataSet.setColor(Color.parseColor("#00BFFF")); // Màu của các cột

        BarData barData = new BarData(dataSet);

        // Cấu hình trục X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(entries.size()); // Số lượng nhãn trên trục X

        // Cấu hình trục Y
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setGranularity(500f); // Độ đơn vị trên trục Y
        yAxisLeft.setAxisMinimum(getMinYValue(entries)); // Giá trị tối thiểu trục Y

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false); // Tắt trục Y bên phải

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
    private float getMinYValue(ArrayList<BarEntry> entries) {
        float minY = 0f;
        for (BarEntry entry : entries) {
            if (entry.getY() < minY) {
                minY = entry.getY();
            }
        }
        return minY;
    }

    @Override
    public void onYearSelected(int year) {
        getListBudget(year);
    }

    public void getListBudget(int year) {
        String startDay = "0101" + year;
        String endDay = "3112" + year;
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        Date startDate, endDate;
        long startTime, endTime;
        try {
            startDate = inputFormat.parse(startDay);
            endDate = inputFormat.parse(endDay);
            String startTimeString = outputFormat.format(startDate);
            String endTimeString = outputFormat.format(endDate);
            startTime = outputFormat.parse(startTimeString).getTime();
            endTime = outputFormat.parse(endTimeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        int type = 2;

        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<ReportInYearDTO> data = ReportApi.api.getYearReport(startTime, endTime, type).execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) requireActivity().runOnUiThread(() -> {
                        tvAverage.setText(String.valueOf(data.body().getAverage()) + "đ");
                        tvSum.setText(String.valueOf(data.body().getSum()) + "đ");
                        tvThu.setText(data.body().getRevenue() + "đ");
                        tvChi.setText(data.body().getExpense() + "đ");

                        if (data.body().getChart() != null) {
                            initData(data.body().getChart(), year);
                        }
                    });
                }
            } catch (HttpException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show());
            }
        });
        thread.start();
    }
}