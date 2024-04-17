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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ExpenseAdapter2;
import com.poly.moneylover.databinding.ActivityOutBinding;
import com.poly.moneylover.models.ChartApi;
import com.poly.moneylover.models.DataDetailsReportModelApi;
import com.poly.moneylover.models.ExpenseItem;
import com.poly.moneylover.models.ExpenseItem2;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.ApiService;
import com.poly.moneylover.utils.Convert;

import java.text.DecimalFormat;
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

public class OutActivity extends AppCompatActivity{

    private ActivityOutBinding binding;

    private ExpenseAdapter2 expenseAdapter;
    private List<ExpenseItem2> expenseItemList;
    private String id;
    private int idColor;
    private SimpleDateFormat numberFormatFullDate = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat numberFormatMonth = new SimpleDateFormat("MM");
    private List<String> listDate;
    private List<Long> listTotal;

    private String startDay, endDay;

    private String name;
    private Calendar calendar;

    private Boolean refreshLayout = false;
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getStringExtra("id");
        idColor = getIntent().getIntExtra("idColor",R.color.black);
        startDay = getIntent().getStringExtra("startDay");
        endDay = getIntent().getStringExtra("endDay");
        name = getIntent().getStringExtra("name");

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
                    refreshLayout = true;
                    setupChart();
                    setupAdapter();
                }
            });

    private void setupChart() {
        // Initialize data
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            entries.add(new BarEntry(i, 0));
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPreferences", MODE_PRIVATE);
        ApiService.apiService.getDataReportDetails("Bearer " + sharedPreferences.getString("token", ""), "1", "1", id).enqueue(new Callback<DataDetailsReportModelApi>() {

            @Override
            public void onResponse(Call<DataDetailsReportModelApi> call, Response<DataDetailsReportModelApi> response) {
                if (response.body() == null || response.body().chart == null) {
                    return;
                }

                String year = numberFormatFullDate.format(calendar.getTime()).split("/")[2];
                for (ChartApi chartApi : response.body().chart) {
                    if (chartApi.getYear() == Integer.parseInt(year) && chartApi.getTotal() > 0) {
                        int month = chartApi.getMonth();
                        entries.set(month - 1, new BarEntry(month, chartApi.getTotal()));
                    }
                }

                BarDataSet dataSet = new BarDataSet(entries, "Doanh thu theo tháng");
                dataSet.setColor(getResources().getColor(idColor));

                ValueFormatter customValueFormatter = new ValueFormatter() {
                    private DecimalFormat decimalFormat = new DecimalFormat("#,##0");

                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "0";
                        } else {
                            return formatValueWithSuffix(value);
                        }
                    }
                    private String formatValueWithSuffix(float value) {
                        if (value >= 1000000) {
                            float valueInMillions = value / 1000000f;
                            return String.format("%.2fM", valueInMillions);
                        } else if (value >= 10000) {
                            float valueInThousands = value / 1000f;
                            return String.format("%.1fK", valueInThousands);
                        } else {
                            return decimalFormat.format(value);
                        }
                    }
                };


                BarData barData = new BarData(dataSet);
                barData.setValueFormatter(customValueFormatter);
                barData.setValueTextSize(7f);
                barData.setBarWidth(0.9f);

                XAxis xAxis = binding.barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setDrawAxisLine(true);
                xAxis.setDrawGridLines(false);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        int month = (int) value;
                        return "T" + month;
                    }
                });

                YAxis leftAxis = binding.barChart.getAxisLeft();
                leftAxis.setAxisMinimum(0f);
                leftAxis.setGranularity(1000f);
                leftAxis.setDrawGridLines(true);

                binding.barChart.getAxisRight().setEnabled(false);
                binding.barChart.setData(barData);
                binding.barChart.getXAxis().setLabelCount(12);
                binding.barChart.setTouchEnabled(false);
                binding.barChart.animateY(100);
                binding.barChart.getDescription().setEnabled(false);
            }

            @Override
            public void onFailure(Call<DataDetailsReportModelApi> call, Throwable t) {

            }
        });
    }
    private void setupAdapter(){
        expenseItemList = new ArrayList<>();
        listDate = new ArrayList<>();
        listTotal = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPreferences", MODE_PRIVATE);


        Log.e("TAG", "setupAdapter: " + startDay + "____" + endDay + "___" + id );

        ApiService.apiService.getDataReportDetails("Bearer " + sharedPreferences.getString("token", ""),startDay, endDay, id).enqueue(new Callback<DataDetailsReportModelApi>() {

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
                                    transaction.getNote(),
                                    transaction.getCategory().getColor())));
                        }
                    }
                }

                Collections.reverse(listDate);

                // If no items, hide the title
                if (expenseItemList.isEmpty()) {
                    binding.title.setVisibility(View.GONE);
                }

                // Create and set adapter
                expenseAdapter = new ExpenseAdapter2(expenseItemList, OutActivity.this);
                expenseAdapter.setOnItemClickListener(expenseItem -> {
                    Intent intent = new Intent(OutActivity.this, UpdateActivity.class);
                    intent.putExtra("data", expenseItem);
                    intent.putExtra("type", 0);
                    intent.putExtra("idCate", id);
                    activityResultLauncher.launch(intent);
                });

                // Set adapter to RecyclerView
                binding.rcvItems.setAdapter(expenseAdapter);
                binding.rcvItems.setItemViewCacheSize(expenseAdapter.getItemCount());
            }


            @Override
            public void onFailure(Call<DataDetailsReportModelApi> call, Throwable t) {

            }
        });
    }
}