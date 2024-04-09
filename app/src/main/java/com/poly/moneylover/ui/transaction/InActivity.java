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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                String year = numberFormatFullDate.format(calendar.getTime()).split("/")[2];
                for (int i = 0; i < response.body().chart.size(); i++) {
                    ChartApi chartApi = response.body().chart.get(i);
                    if(chartApi.getYear() == Integer.parseInt(year)) {
                        entries.add(new BarEntry(chartApi.getMonth(), chartApi.getTotal()));
                    }
                }

                BarDataSet dataSet = new BarDataSet(entries, "Doanh thu theo tháng");
                dataSet.setColor(Color.rgb(255, 165, 0));

                // Tạo BarData object và đặt giá trị
                BarData barData = new BarData(dataSet);
                ValueFormatter customValueFormatter = new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return value-0 > 0.1f ? String.valueOf(Convert.convertNumber((long)value)) : "";
                    }
                };
                barData.setValueFormatter(customValueFormatter);

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
                leftAxis.setGranularity(1000f); // Đặt độ chia giữa các số trên trục Y
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
                if (response.body() == null || response.body().transactions == null) {
                    return;
                }

                Map<String, Long> dailyTotalMap = new HashMap<>(); // Map to store total per day

                for (Transaction transaction : response.body().transactions) {
                    String transactionDate = Convert.getDateConvert(transaction.getDay());

                    // Calculate total per day

                    Long total = dailyTotalMap.getOrDefault(transactionDate, 0L);
                    total += transaction.getPrice();
                    dailyTotalMap.put(transactionDate, total);
                }
                Long titleTotal = Long.valueOf(0);
                for (int i = 0; i < response.body().transactions.size(); i++) {
                    Transaction transaction = response.body().transactions.get(i);
                    titleTotal += response.body().transactions.get(i).getPrice();
                    binding.title.setText(name + " (T" + Integer.parseInt(numberFormatMonth.format(calendar.getTime())) + ") " + Convert.convertNumber(titleTotal) + "₫");
                }

                // Clear existing lists before populating
                listDate.clear();
                listTotal.clear();
                expenseItemList.clear();

                for (Map.Entry<String, Long> entry : dailyTotalMap.entrySet()) {
                    String date = entry.getKey();
                    Long total = entry.getValue();

                    // Populate lists
                    listDate.add(date);

                    listTotal.add(total);

                    // Add expense item for the day
                    expenseItemList.add(new ExpenseItem2(date, new ExpenseItem(total)));

                    // Add individual transactions for the day
                    for (Transaction transaction : response.body().transactions) {
                        if (Convert.getDateConvert(transaction.getDay()).equals(date)) {

                            expenseItemList.add(new ExpenseItem2(date, new ExpenseItem(transaction.getTransactionId(),
                                    transaction.getCategory().getIcon(),
                                    name,
                                    numberFormatFullDate.format(transaction.getDay()),
                                    String.valueOf(transaction.getPrice()),
                                    transaction.getNote())));
                        }
                    }
                }

                Collections.reverse(listDate);

                // If no items, hide the title
                if (expenseItemList.isEmpty()) {
                    binding.title.setVisibility(View.GONE);
                }

                // Create and set adapter
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