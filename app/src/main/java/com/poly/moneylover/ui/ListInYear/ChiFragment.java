package com.poly.moneylover.ui.ListInYear;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemReportAdapter;
import com.poly.moneylover.interfaces.ItemOnClickReport;
import com.poly.moneylover.interfaces.YearListener;
import com.poly.moneylover.models.ReportModels.DetailReportDTO;
import com.poly.moneylover.models.ReportModels.ReportCategoryDTO;
import com.poly.moneylover.models.ReportModels.ReportCategoryInYearDTO;
import com.poly.moneylover.models.ReportModels.ReportInYearDTO;
import com.poly.moneylover.network.ReportApi;
import com.poly.moneylover.ui.category.EditActivity;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;

public class ChiFragment extends Fragment implements YearListener, ItemOnClickReport {
    private TextView tvSum;
    private PieChart pieChart;
    private RecyclerView rcvCategory;
    private ItemReportAdapter adapter;
    private int year;
    public static final String DATA_DETAIL_REPORT = "DATA_DETAIL_REPORT";
    public static final String DATA_TITLE = "DATA_TITLE";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi2, container, false);
        tvSum = view.findViewById(R.id.tvSum);
        pieChart = view.findViewById(R.id.pieChart);
        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemReportAdapter(this,getContext());
        rcvCategory.setAdapter(adapter);

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
    @Override
    public void onYearSelected(int year) {
        getReport(year);
        this.year = year;
    }

    public void getReport(int year) {
        String startDay = "0101" + year;
        String endDay = "3112" + year;
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        Date startDate, endDate;
        long startTime, endTime;
        try {
            startDate = inputFormat.parse(startDay);
            endDate = inputFormat.parse(endDay);
            String startTimeString = outputFormat.format(startDate);
            String endTimeString = outputFormat.format(endDate);
            startTime = outputFormat.parse(startTimeString).getTime();
            endTime = outputFormat.parse(endTimeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        int type = 0;


        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<ReportCategoryInYearDTO> data = ReportApi.api.getReport(startTime, endTime, type).execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) requireActivity().runOnUiThread(() -> {
                        tvSum.setText(String.valueOf(data.body().getExpense()) + "đ");
                        if (data.body().getCategory() != null) {
                            setDataChart(data.body().getCategory());
                            adapter.setList(data.body().getCategory());
                        }
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

    public void getDetailReport(String id, String title) {
        String startDay = "0101" + year;
        String endDay = "3112" + year;
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        Date startDate, endDate;
        long startTime, endTime;
        try {
            startDate = inputFormat.parse(startDay);
            endDate = inputFormat.parse(endDay);
            String startTimeString = outputFormat.format(startDate);
            String endTimeString = outputFormat.format(endDate);
            startTime = outputFormat.parse(startTimeString).getTime();
            endTime = outputFormat.parse(endTimeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<DetailReportDTO> data = ReportApi.api.getReportDetailCategory(startTime, endTime, id).execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) requireActivity().runOnUiThread(() -> {
                        Intent intent = new Intent(requireContext(), DetailReportActivity.class);
                        intent.putExtra(DATA_DETAIL_REPORT, data.body());
                        intent.putExtra(DATA_TITLE, title);
                        requireActivity().startActivity(intent);
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
        String title = categoryDTO.getName() + " (" + this.year +")";
        getDetailReport(categoryDTO.get_id(), title);
    }
}