package com.poly.moneylover.ui.transaction.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.poly.moneylover.adapters.ReportAdapter;
import com.poly.moneylover.databinding.FragmentOutcomeBinding;
import com.poly.moneylover.models.CategoryModelApi;
import com.poly.moneylover.models.DataReportModelApi;
import com.poly.moneylover.models.EventbusModel;
import com.poly.moneylover.models.ExpenseItem;
import com.poly.moneylover.network.ApiService;
import com.poly.moneylover.ui.transaction.OutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OutcomeFragment extends Fragment {

    private FragmentOutcomeBinding binding;
    private ReportAdapter reportAdapter;
    private List<ExpenseItem> expenseItemList;
    private String startTime, endTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOutcomeBinding.inflate(inflater, container, false);

        EventBus.getDefault().register(this);
        setupPieChart();
        return binding.getRoot();
    }

    private void setupPieChart() {
        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.getDescription().setText("");
        binding.pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        binding.pieChart.getLegend().setWordWrapEnabled(true);
        binding.pieChart.getLegend().setEnabled(false);
        binding.pieChart.setExtraOffsets(10, 10, 10, 10);
        binding.pieChart.setRenderer(new CustomPieChartRenderer(binding.pieChart));
        binding.pieChart.setMarker(new PieChartMarkerView(binding.pieChart));
        binding.pieChart.setRotationEnabled(false);
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    getData();
                }
            });


    @Subscribe
    public void onEvent(EventbusModel event) {
        startTime = event.getTimeStampStart();
        endTime = event.getTimeStampEnd();

        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        getData();
    }

    private void getData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("myPreferences", MODE_PRIVATE);

        ApiService.apiService.getDataReport("Bearer " + sharedPreferences.getString("token", ""), startTime, endTime, 0).enqueue(new Callback<DataReportModelApi>() {

            @Override
            public void onResponse(@NonNull Call<DataReportModelApi> call, @NonNull Response<DataReportModelApi> response) {
                if (response.body() == null || response.body().category == null) {
                    return;
                }

                ArrayList<Integer> listColors = new ArrayList<>();
                expenseItemList = new ArrayList<>();
                ArrayList<PieEntry> entries = new ArrayList<>();
                expenseItemList = new ArrayList<>();
                PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");

                for (int i = 0; i < response.body().category.size(); i++) {
                    CategoryModelApi categoryModelApi = response.body().category.get(i);
                    entries.add(new PieEntry(categoryModelApi.total, categoryModelApi.name));
                    expenseItemList.add(new ExpenseItem(categoryModelApi._id, categoryModelApi.icon, categoryModelApi.name, "", String.valueOf(categoryModelApi.total)));
                    try {
                        listColors.add(ContextCompat.getColor(requireContext(), categoryModelApi.color));
                    } catch (Exception e) {
                        listColors.add(Color.YELLOW);
                    }
                }
                dataSet.setColors(listColors);
                PieData data1 = new PieData(dataSet);
                data1.setValueTextSize(15f);
                dataSet.setUsingSliceColorAsValueLineColor(true);
                dataSet.setAutomaticallyDisableSliceSpacing(true);
                dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                dataSet.setValueLinePart1Length(0.5f);
                dataSet.setValueLinePart2Length(0.2f);
                dataSet.setSliceSpace(1.5f);

                binding.pieChart.setData(data1);
                binding.pieChart.invalidate();
                // Initialize adapter
                reportAdapter = new ReportAdapter(expenseItemList, requireContext(), 0);

                // Set adapter to RecyclerView
                binding.rcvItem.setAdapter(reportAdapter);

                reportAdapter.setOnItemClickListener(expenseItem -> {
                    // Xử lý khi một item được click, ví dụ chuyển sang Activity mới
                    Intent intent = new Intent(getContext(), OutActivity.class);
                    intent.putExtra("name", expenseItem.getTitle());
                    intent.putExtra("id", expenseItem.getId());
                    intent.putExtra("startDay", startTime);
                    intent.putExtra("endDay", endTime);
                    activityResultLauncher.launch(intent);
                });
            }

            @Override
            public void onFailure(Call<DataReportModelApi> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            // Here, no request code
            if (data.getBooleanExtra("RESULT", false)) {
                getData();
            }
        }
    }
}