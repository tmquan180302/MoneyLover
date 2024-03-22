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

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
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
    public Pie pie;

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
        pie = AnyChart.pie();
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
                List<DataEntry> data = new ArrayList<>();

                for (int i = 0; i < response.body().category.size(); i++) {
                    CategoryModelApi categoryModelApi = response.body().category.get(i);
                    data.add(new ValueDataEntry(categoryModelApi.name, categoryModelApi.total));
                    expenseItemList.add(new ExpenseItem(categoryModelApi._id, categoryModelApi.icon, categoryModelApi.name, "", String.valueOf(categoryModelApi.total)));
                    try {
                        listColors.add(ContextCompat.getColor(requireContext(), categoryModelApi.color));
                    } catch (Exception e) {
                        listColors.add(Color.YELLOW);
                    }
                }

                String[] listColor = new String[listColors.size()];
                for (int i = 0; i < listColors.size(); i++) {
                    listColor[i] = "#"+Integer.toHexString(listColors.get(i)).substring(2);
                }

                try {
                    pie.palette(listColor);

                    pie.animation(true);
                    pie.data(data);

                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Chi tiêu")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    binding.pieChart2.setChart(pie);
                }catch (Exception e){
                    e.printStackTrace();
                }


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