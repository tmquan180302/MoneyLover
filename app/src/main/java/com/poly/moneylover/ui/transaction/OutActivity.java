package com.poly.moneylover.ui.transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.poly.moneylover.R;
import com.poly.moneylover.databinding.ActivityOutBinding;
import com.poly.moneylover.adapters.ExpenseAdapter;
import com.poly.moneylover.models.ExpenseItem;

import java.util.ArrayList;
import java.util.List;

public class OutActivity extends AppCompatActivity {

    private ActivityOutBinding binding;

    private ExpenseAdapter expenseAdapter;
    private List<ExpenseItem> expenseItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupBar();
        setupAdapter();

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result_key", "your_result_data"); // Thêm dữ liệu nếu cần
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
    private void setupBar() {
        // Khởi tạo dữ liệu
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 50000));
        entries.add(new BarEntry(2, 60000));
        // Thêm các tháng khác tương tự

        // Tạo dataset và đặt màu sắc
        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu theo tháng");
        dataSet.setColor(Color.rgb(51, 102, 255));

        // Tạo BarData object và đặt giá trị
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        // Cấu hình trục X
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                // Chuyển đổi giá trị tháng thành chuỗi
                int month = (int) value;
                return String.valueOf(month);
            }
        });

        // Cấu hình trục Y
        YAxis leftAxis = binding.barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // Giá trị tối thiểu của trục Y
        leftAxis.setGranularity(10000f); // Đặt độ chia giữa các số trên trục Y
        leftAxis.setDrawGridLines(true);

        // Hiển thị giá trị trục Y bên phải
        binding.barChart.getAxisRight().setEnabled(false);

        // Thiết lập dữ liệu vào biểu đồ
        binding.barChart.setData(barData);

        // Vô hiệu hóa hiệu ứng chuyển động nếu cần
        binding.barChart.animateY(1000);
    }
    private void setupAdapter() {
        // Initialize data
        expenseItemList = new ArrayList<>();
        expenseItemList.add(new ExpenseItem(R.drawable.icon_coffee, "Ăn uống", "2022-02-03", "+5,000,000"));

        // Initialize adapter
        expenseAdapter = new ExpenseAdapter(expenseItemList, this);

        // Set adapter to RecyclerView
        binding.rcvItems.setAdapter(expenseAdapter);
        // Đặt sự kiện click cho RecyclerView
    }
}