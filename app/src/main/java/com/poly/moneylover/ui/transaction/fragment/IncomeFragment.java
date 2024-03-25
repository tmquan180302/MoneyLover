package com.poly.moneylover.ui.transaction.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.poly.moneylover.R;
import com.poly.moneylover.databinding.FragmentIncomeBinding;
import com.poly.moneylover.adapters.ExpenseAdapter;
import com.poly.moneylover.models.ExpenseItem;
import com.poly.moneylover.ui.transaction.InActivity;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends Fragment {

    private FragmentIncomeBinding binding;
    private ExpenseAdapter expenseAdapter;
    private List<ExpenseItem> expenseItemList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIncomeBinding.inflate(inflater, container, false);

        setupAdapter();
        setupPieChart();
        loadChartData();
        return binding.getRoot();
    }

    private void setupAdapter() {
        // Initialize data
        expenseItemList = new ArrayList<>();
        expenseItemList.add(new ExpenseItem(R.drawable.icon_coffee, "Lương", "2022-02-03", "+5,000,000"));
        expenseItemList.add(new ExpenseItem(R.drawable.icon_coin, "Thưởng", "2022-02-03", "+500,000"));

        // Initialize adapter
        expenseAdapter = new ExpenseAdapter(expenseItemList, requireContext());

        // Set adapter to RecyclerView
        binding.rcvItem.setAdapter(expenseAdapter);
        // Đặt sự kiện click cho RecyclerView
        expenseAdapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExpenseItem expenseItem) {
                // Xử lý khi một item được click, ví dụ chuyển sang Activity mới
                Intent intent = new Intent(getContext(), InActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(20f, "Lương"));
        entries.add(new PieEntry(30f, "Thưởng"));

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(Color.RED, Color.GREEN, Color.BLUE);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        binding.pieChart.setData(data);
    }

    private void setupPieChart() {
        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setExtraOffsets(5, 10, 5, 5);

        binding.pieChart.setDragDecelerationFrictionCoef(0.95f);

        binding.pieChart.setDrawHoleEnabled(true);
        binding.pieChart.setHoleColor(Color.WHITE);
        binding.pieChart.setTransparentCircleRadius(61f);

        binding.pieChart.setRotationEnabled(true);
        binding.pieChart.setHighlightPerTapEnabled(true);
    }
}