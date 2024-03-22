package com.poly.moneylover.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.TabPagerAdapter;
import com.poly.moneylover.constants.Constants;
import com.poly.moneylover.databinding.FragmentReportBinding;
import com.poly.moneylover.models.DataReportModelApi;
import com.poly.moneylover.models.EventbusModel;
import com.poly.moneylover.network.ApiService;
import com.poly.moneylover.ui.dialog.MonthYearPickerDialog;
import com.poly.moneylover.utils.Convert;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;
    private Calendar calendar, calendarStart, calendarEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);

        configViewpager();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar = Calendar.getInstance();
        calendarStart = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();

        calendarStart.set(Calendar.DAY_OF_MONTH, 1);

        calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));

        binding.tvDateFull.setText(getDayMonth(0));
        SimpleDateFormat numberFormat = new SimpleDateFormat("MM/yyyy");
        binding.tvDateMonth.setText(numberFormat.format(calendar.getTime()));

        getNewData(0, String.valueOf(calendarStart.getTimeInMillis()), String.valueOf(calendarEnd.getTimeInMillis()));

        Constants.START_DAY_REPORT = String.valueOf(calendarStart.getTimeInMillis());
        Constants.END_DAY_REPORT = String.valueOf(calendarEnd.getTimeInMillis());

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            EventBus.getDefault().post(new EventbusModel(String.valueOf(calendarStart.getTimeInMillis()), String.valueOf(calendarEnd.getTimeInMillis())));
        }, 500);

        binding.icBack.setOnClickListener(v -> {
            binding.tvDateFull.setText(getDayMonth(-1));
            binding.tvDateMonth.setText(numberFormat.format(calendar.getTime()));
        });

        binding.icNext.setOnClickListener(v -> {
            binding.tvDateFull.setText(getDayMonth(1));
            binding.tvDateMonth.setText(numberFormat.format(calendar.getTime()));

        });

        binding.layoutPickDate.setOnClickListener(v -> {
            MonthYearPickerDialog monthYearPickerDialog = new MonthYearPickerDialog((month, year) -> {
                calendar.set(Calendar.YEAR, Integer.parseInt(year));
                calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                binding.tvDateMonth.setText(numberFormat.format(calendar.getTime()));
                binding.tvDateFull.setText(getDayMonth(0));
            }, calendar);

            monthYearPickerDialog.show(getFragmentManager(), "");
        });

        binding.search.setOnClickListener(v -> {

        });

        binding.tvDateFull.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calendarStart.set(Calendar.DAY_OF_MONTH, 1);
                calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));

                EventBus.getDefault().post(new EventbusModel(String.valueOf(calendarStart.getTimeInMillis()), String.valueOf(calendarEnd.getTimeInMillis())));

                getNewData(0, String.valueOf(calendarStart.getTimeInMillis()), String.valueOf(calendarEnd.getTimeInMillis()));

                Constants.START_DAY_REPORT = String.valueOf(calendarStart.getTimeInMillis());
                Constants.END_DAY_REPORT = String.valueOf(calendarEnd.getTimeInMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private String getDayMonth(int value) {
        calendar.add(Calendar.MONTH, value);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        setCalendar(year, month, day);
        return "(01/" + (month + 1) + " - " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + (month + 1) + ")";
    }

    private void setCalendar(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendarStart.set(Calendar.YEAR, year);
        calendarEnd.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month);
        calendarEnd.set(Calendar.MONTH, month);
    }

    private void configViewpager() {
        TabPagerAdapter adapterStatic = new TabPagerAdapter(requireActivity());

        binding.viewpageInput.setAdapter(adapterStatic);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewpageInput, true, true, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chi tiêu");
                    break;
                case 1:
                    tab.setText("Thu Nhập");
                    break;
            }
        });
        tabLayoutMediator.attach();
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.orange));
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(requireContext(), R.color.black), ContextCompat.getColor(requireContext(), R.color.orange));
    }

    private void getNewData(int type, String timeStampStart, String timeStampEnd) {
        final SharedPreferences[] sharedPreferences = {requireActivity().getSharedPreferences("myPreferences", MODE_PRIVATE)};

        ApiService.apiService.getDataReport("Bearer " + sharedPreferences[0].getString("token", ""), timeStampStart, timeStampEnd, type).enqueue(new Callback<DataReportModelApi>() {

            @Override
            public void onResponse(Call<DataReportModelApi> call, Response<DataReportModelApi> response) {
                if (response.body() == null || response.body().category == null) {
                    return;
                }

                binding.tvChi.setText("-" + Convert.convertNumber(response.body().expense) + "₫");
                binding.tvThu.setText("+" + Convert.convertNumber(response.body().revenue) + "₫");


                if (response.body().total > 0){
                    binding.tvThuChi.setText("+"+Convert.convertNumber(response.body().total) + "₫");
                }else {
                    binding.tvThuChi.setText("-"+Convert.convertNumber(response.body().total) + "₫");
                }

                Constants.TOTAL_OUT = response.body().expense;
                Constants.TOTAL_IN = response.body().revenue;
            }

            @Override
            public void onFailure(Call<DataReportModelApi> call, Throwable t) {

            }
        });
    }

}