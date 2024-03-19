package com.poly.moneylover.ui.ListInYear;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.poly.moneylover.R;

import java.util.ArrayList;


public class ThuFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tieu, container, false);
        PieChart pieChart = view.findViewById(R.id.pieChart);

        // Tạo dữ liệu mẫu cho biểu đồ hình tròn
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f, "Ăn uống"));
        entries.add(new PieEntry(20f, "Nhảy"));
        entries.add(new PieEntry(50f, "Ăn cức"));
        entries.add(new PieEntry(50f, "Ăn cức"));

        // Tạo một dataSet
        PieDataSet dataSet = new PieDataSet(entries, "");

        // Đặt màu cho các phần trong biểu đồ hình tròn
        dataSet.setColors(Color.BLUE, Color.GREEN, Color.RED);
        // Tắt hiển thị nhãn trên từng phần của biểu đồ
        dataSet.setDrawValues(false); // bỏ giá trị trên bểu đồ
        // Tạo một PieData từ dataSet
        PieData pieData = new PieData(dataSet);

        // Thiết lập các thuộc tính cho biểu đồ hình tròn
        pieChart.setData(pieData);
        pieChart.setCenterText(""); // Văn bản ở trung tâm
        pieChart.setCenterTextSize(18f);

        // Hiển thị thông tin trên các phần trong biểu đồ
        pieData.setDrawValues(false); // bỏ giá trị trên biểudđô
        pieData.setValueTextSize(14f);
        pieChart.setDrawEntryLabels(false); // bor text trên biểu đồ
        pieChart.setDescription(null); // tăắt chữ Description label cuối biểu đồ

        // Cập nhật biểu đồ
        pieChart.invalidate();
        return view;
    }
}