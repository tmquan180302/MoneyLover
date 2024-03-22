package com.poly.moneylover.ui.transaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.poly.moneylover.adapters.ExpenseAdapter2;
import com.poly.moneylover.databinding.ActivityInBinding;
import com.poly.moneylover.models.ChartApi;
import com.poly.moneylover.models.DataDetailsReportModelApi;
import com.poly.moneylover.models.ExpenseItem;
import com.poly.moneylover.models.ExpenseItem2;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.ApiService;
import com.poly.moneylover.utils.Convert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InActivity extends AppCompatActivity {

    private ActivityInBinding binding;
    private ExpenseAdapter2 expenseAdapter;
    private List<ExpenseItem2> expenseItemList;
    private List<String> listDate;
    private List<BarEntry> entries;
    private List<Long> listTotal;

    private String id;
    private SimpleDateFormat numberFormatFullDate = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat numberFormatMonth = new SimpleDateFormat("MM");
    private Boolean refreshLayout = false;
    private String startDay, endDay;
    private String name;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        startDay = getIntent().getStringExtra("startDay");
        endDay = getIntent().getStringExtra("endDay");
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(startDay));

        binding.iconBack.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("RESULT", refreshLayout); // Thêm dữ liệu nếu cần
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
        setupChart();
        setupAdapter();
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    setupChart();
                    setupAdapter();
                    refreshLayout = true;
                    Log.e("RESULT:", "OK");
                }
            });

    private void setupChart() {
        // Initialize data
        entries = new ArrayList<>();
        entries.add(new BarEntry(1, 0));
        entries.add(new BarEntry(2, 0));
        entries.add(new BarEntry(3, 0));
        entries.add(new BarEntry(4, 0));
        entries.add(new BarEntry(5, 0));
        entries.add(new BarEntry(6, 0));
        entries.add(new BarEntry(7, 0));
        entries.add(new BarEntry(8, 0));
        entries.add(new BarEntry(9, 0));
        entries.add(new BarEntry(10, 0));
        entries.add(new BarEntry(11, 0));
        entries.add(new BarEntry(12, 0));

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPreferences", MODE_PRIVATE);
        ApiService.apiService.getDataReportDetails("Bearer " + sharedPreferences.getString("token", ""), "1", "1", id).enqueue(new Callback<DataDetailsReportModelApi>() {

            @Override
            public void onResponse(Call<DataDetailsReportModelApi> call, Response<DataDetailsReportModelApi> response) {
                if (response.body() == null || response.body().chart == null) {
                    return;
                }

                for (int i = 0; i < response.body().chart.size(); i++) {
                    ChartApi chartApi = response.body().chart.get(i);
                    entries.add(new BarEntry(chartApi.getMonth(), chartApi.getTotal()));
                }

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
                        return "T" + month;
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
                binding.barChart.getXAxis().setLabelCount(entries.size());
                binding.barChart.setTouchEnabled(false);

                // Vô hiệu hóa hiệu ứng chuyển động nếu cần
                binding.barChart.animateY(1000);
                binding.barChart.getDescription().setEnabled(false);
            }

            @Override
            public void onFailure(Call<DataDetailsReportModelApi> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void setupAdapter() {
        expenseItemList = new ArrayList<>();
        listDate = new ArrayList<>();
        listTotal = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPreferences", MODE_PRIVATE);
        ApiService.apiService.getDataReportDetails("Bearer " + sharedPreferences.getString("token", ""), startDay, endDay, id).enqueue(new Callback<DataDetailsReportModelApi>() {

            @Override
            public void onResponse(Call<DataDetailsReportModelApi> call, Response<DataDetailsReportModelApi> response) {
                Log.e("TAG", "onResponse: " + response.body());
                if (response.body() == null || response.body().transactions == null) {
                    return;
                }

                Long total = Long.valueOf(0);
                for (int i = 0; i < response.body().transactions.size(); i++) {
                    Transaction transaction = response.body().transactions.get(i);

                    total += response.body().transactions.get(i).getPrice();

                    binding.title.setText(name + "(T" + Integer.parseInt(numberFormatMonth.format(calendar.getTime())) + ") " + Convert.convertNumber(total) + "₫");

                    if (!listDate.contains(Convert.getDateConvert(transaction.getDay()))) {
                        listDate.add(Convert.getDateConvert(transaction.getDay()));
                        listTotal.add(total);
                    }
                }

                Collections.reverse(listDate);

                for (int i = 0; i < listDate.size(); i++) {
                    expenseItemList.add(new ExpenseItem2(listDate.get(i), new ExpenseItem(listTotal.get(i))));
                    Collections.reverse(response.body().transactions);

                    for (int j = 0; j < response.body().transactions.size(); j++) {
                        Transaction transaction = response.body().transactions.get(j);
                        if (Convert.getDateConvert(transaction.getDay()).equals(listDate.get(i))) {
                            expenseItemList.add(new ExpenseItem2(listDate.get(i), new ExpenseItem(transaction.getTransactionId(), transaction.getCategory().getIcon(), name, numberFormatFullDate.format(transaction.getDay()), String.valueOf(transaction.getPrice()), transaction.getNote())));
                        }
                    }
                }

                if (expenseItemList.size() ==0){
                    binding.title.setVisibility(View.GONE);
                }
                // Initialize adapter
                expenseAdapter = new ExpenseAdapter2(expenseItemList, InActivity.this);
                expenseAdapter.setOnItemClickListener(expenseItem -> {
                    Intent intent = new Intent(InActivity.this, UpdateActivity.class);
                    intent.putExtra("data", expenseItem);
                    intent.putExtra("type", 1);
                    intent.putExtra("idCate", id);
                    activityResultLauncher.launch(intent);
                });

                // Set adapter to RecyclerView
                binding.rcvItems.setAdapter(expenseAdapter);
                binding.rcvItems.setItemViewCacheSize(expenseAdapter.getItemCount());
            }

            @Override
            public void onFailure(Call<DataDetailsReportModelApi> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}