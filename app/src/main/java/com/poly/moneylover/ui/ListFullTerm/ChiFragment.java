package com.poly.moneylover.ui.ListFullTerm;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemReportAdapter;
import com.poly.moneylover.interfaces.ItemOnClickReport;
import com.poly.moneylover.models.ReportModels.ReportCategoryDTO;
import com.poly.moneylover.network.ReportApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;

public class ChiFragment extends Fragment implements ItemOnClickReport {
    private PieChart pieChart;
    private RecyclerView rcvCategory;
    private ItemReportAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_thu2, container, false);
        pieChart = view.findViewById(R.id.pieChart);
        rcvCategory = view.findViewById(R.id.rcvCategory);

        rcvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemReportAdapter(this,getContext());
        rcvCategory.setAdapter(adapter);

        getReport();
        return view;
    }
    public void setDataChart(List<ReportCategoryDTO> list) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        List<Integer> listColor = new ArrayList<>();

        Resources resources = getResources();

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                entries.add(new PieEntry(list.get(i).getPercent(), list.get(i).getName()));
                int colorId = list.get(i).getColor();
                if (isValidColorResource(resources, colorId)) {
                    int color = ContextCompat.getColor(getContext(), colorId);
                    listColor.add(color);
                } else {
                    listColor.add(list.get(i).getColor());
                }
            }
        } else {
            entries.add(new PieEntry(100f, "Chi tiêu"));
            int color = ContextCompat.getColor(getContext(), R.color.orange2);
            listColor.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(listColor);
        dataSet.setDrawValues(false);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(18f);
        pieData.setDrawValues(false);
        pieData.setValueTextSize(14f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDescription(null);
        pieChart.invalidate();
    }

    private boolean isValidColorResource(Resources resources, int colorId) {
        try {
            resources.getColor(colorId);
            return true;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }

    public void getReport() {
        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<List<ReportCategoryDTO>> data = ReportApi.api.getAllReport(0).execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) requireActivity().runOnUiThread(() -> {
                        setDataChart(data.body());
                        adapter.setList(data.body());
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

    @Override
    public void onSelectdReportCategory(ReportCategoryDTO categoryDTO) {

    }
}