package com.poly.moneylover.ui.InYear;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.poly.moneylover.R;

import java.util.ArrayList;


public class TongFragment extends Fragment {


    public TongFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tong, container, false);
        BarChart barChart = view.findViewById(R.id.barChart);

        // Tạo dữ liệu biểu đồ cột
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 2000f));
        entries.add(new BarEntry(2, 150f));
        entries.add(new BarEntry(3, 150f));
        entries.add(new BarEntry(4, 50f));
        entries.add(new BarEntry(5, 100f));
        entries.add(new BarEntry(6, 100f));
        entries.add(new BarEntry(7, 500f));
        entries.add(new BarEntry(8, 100f));
        entries.add(new BarEntry(9, 10f));
        entries.add(new BarEntry(10, 100f));
        entries.add(new BarEntry(11, 100f));
        entries.add(new BarEntry(12, 100f));


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
        yAxisLeft.setAxisMinimum(0f); // Giá trị tối thiểu trục Y

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false); // Tắt trục Y bên phải

        // Tạo đối tượng BarChart
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false); // Tắt mô tả
        barChart.setFitBars(true);
        barChart.invalidate(); // Cập nhật biểu đồ

        return view;
    }
}