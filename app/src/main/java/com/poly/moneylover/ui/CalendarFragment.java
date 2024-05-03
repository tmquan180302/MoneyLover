package com.poly.moneylover.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.CalendarAdapter;
import com.poly.moneylover.adapters.TransactionAdapter;
import com.poly.moneylover.interfaces.ItemOnclickCalendar;
import com.poly.moneylover.interfaces.ItemOnclickTransaction;
import com.poly.moneylover.models.Budget;
import com.poly.moneylover.models.CalendarResponse;
import com.poly.moneylover.models.CalendarApp;
import com.poly.moneylover.models.Response.ResCalendar;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.BudgetApi;
import com.poly.moneylover.network.CalendarApi;
import com.poly.moneylover.ui.ThuChiCoDinh.ThemSuaActivity;
import com.poly.moneylover.ui.dialog.MonthYearPickerDialog;
import com.poly.moneylover.ui.transaction.SearchTransactionActivity;
import com.poly.moneylover.ui.transaction.UpdateTransactionActivity;
import com.poly.moneylover.utils.CalendarUtils;
import com.poly.moneylover.utils.Convert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment implements ItemOnclickCalendar, ItemOnclickTransaction {


    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    private ImageButton imbPre, imbNext;
    private TextView tvMothYear, tvExpense, tvRevenue, tvTotal, tvSearch;
    private RecyclerView rcvCalendar, rcvTransaction;
    private CalendarAdapter calendarAdapter;
    private ArrayList<CalendarApp> listCalendarApp;

    /// Adapter Transaction;

    private TransactionAdapter transactionAdapter;
    private ArrayList<Transaction> listTransaction;

    private Calendar calendar;

    private CalendarApp calendarApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        calendarApp = new CalendarApp(LocalDate.now(), 0, 0);
        CalendarUtils.selectedDate = LocalDate.now();
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+7"));
        pickDateAction();
        search();
        setMonthView();
        previousMonthAction();
        nextMonthAction();

    }

    private void pickDateAction() {
        tvMothYear.setOnClickListener(v -> {
            MonthYearPickerDialog monthYearPickerDialog = new MonthYearPickerDialog((month, year) -> {
                calendar.set(Calendar.YEAR, Integer.parseInt(year));
                calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                CalendarUtils.selectedDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
                setMonthView();
            }, calendar);
            monthYearPickerDialog.show(getFragmentManager(), "");
        });
    }

    // setLayout
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    setMonthView();
                }
            });

    private void search() {
        tvSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchTransactionActivity.class);
            startActivity(intent);
        });
    }

    private void nextMonthAction() {
        imbNext.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
            setMonthView();
        });
    }

    private void previousMonthAction() {
        imbPre.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
            setMonthView();
        });

    }


    private void setMonthView() {
        tvMothYear.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        listCalendarApp = CalendarUtils.daysInMonthArray();

        calendarAdapter = new CalendarAdapter(listCalendarApp, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        rcvCalendar.setLayoutManager(layoutManager);
        rcvCalendar.setAdapter(calendarAdapter);


        listTransaction = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvTransaction.setLayoutManager(linearLayoutManager);

        transactionAdapter = new TransactionAdapter(listTransaction, getContext(), this);
        rcvTransaction.setAdapter(transactionAdapter);

        getCalendar();

    }

    private void getCalendar() {
        String startDay = CalendarUtils.startDayOfMonthInMileSecond(CalendarUtils.selectedDate);
        String endDay = CalendarUtils.endDayOfMonthInMileSecond(CalendarUtils.selectedDate);

        Thread thread = new Thread(() -> {
            try {
                CalendarApi.api.getScreenCalendar(startDay, endDay).enqueue(new Callback<ResCalendar>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<ResCalendar> call, Response<ResCalendar> response) {
                        if (response.isSuccessful()) {

                            tvExpense.setText(Convert.formatNumberCurrent(String.valueOf(response.body().getExpense())) + "đ");
                            tvRevenue.setText(Convert.formatNumberCurrent(String.valueOf(response.body().getRevenue())) + "đ");
                            tvTotal.setText(Convert.formatNumberCurrent(String.valueOf(response.body().getTotal())) + "đ");

                            updateCalendar(response.body().getCalendar());
                            updateTransaction(response.body().getTransactions());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResCalendar> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        thread.start();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTransaction(List<Transaction> transactions) {

        listTransaction.clear();
        listTransaction.addAll(transactions);
        transactionAdapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCalendar(List<CalendarResponse> calendarResponse) {

        ArrayList<CalendarApp> listTransform = new ArrayList<>();

        for (int i = 0; i < calendarResponse.size(); i++) {
            listTransform.add(new CalendarApp(LocalDate.of(calendarResponse.get(i).getYear(),
                    calendarResponse.get(i).getMonth(), calendarResponse.get(i).getDay()),
                    calendarResponse.get(i).getExpense(), calendarResponse.get(i).getRevenue()));
        }

        for (CalendarApp app1 : listTransform) {
            LocalDate date1 = app1.getDate();
            long expense1 = app1.getExpense();
            long revenue1 = app1.getRevenue();

            for (CalendarApp app2 : listCalendarApp) {
                LocalDate date2 = app2.getDate();

                if (date1.equals(date2)) {
                    app2.setExpense(expense1);
                    app2.setRevenue(revenue1);
                    break;
                }
            }
        }
        calendarAdapter.notifyDataSetChanged();
    }


    private void initView(View view) {
        imbPre = view.findViewById(R.id.imb_Pre);
        imbNext = view.findViewById(R.id.imb_Next);
        tvSearch = view.findViewById(R.id.tv_search);
        tvMothYear = view.findViewById(R.id.tv_monthYear);
        tvExpense = view.findViewById(R.id.tv_expense);
        tvRevenue = view.findViewById(R.id.tv_revenue);
        tvTotal = view.findViewById(R.id.tv_total);
        rcvCalendar = view.findViewById(R.id.rcv_calendar);
        rcvTransaction = view.findViewById(R.id.rcv_transaction);
    }


    @Override
    public void onSelectedCalendar(CalendarApp date) {

        int position = findPositionByDay(date.getDate().toString());

        if (checkDoubleClick(date)) {
            LocalDateTime startOfMonth = date.getDate().atStartOfDay();
            ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            long milliseconds = startOfMonth.atZone(zoneId).toInstant().toEpochMilli();

            FragmentTransaction fragmentManager1 = getFragmentManager().beginTransaction();
            fragmentManager1.add(R.id.fragment_container_view, InputFragment.newInstanceCalendar(milliseconds), "fragInput");
            fragmentManager1.commit();
        } else {
            calendarApp = new CalendarApp(date.getDate(), date.getExpense(), date.getRevenue());
            if (position != -1) {
                rcvTransaction.smoothScrollToPosition(position);
            }
        }
    }

    private boolean checkDoubleClick(CalendarApp calendarCheck) {
        return calendarCheck.getDate().equals(calendarApp.getDate()) && calendarCheck.getExpense() == calendarCheck.getExpense() && calendarCheck.getRevenue() == calendarApp.getRevenue();
    }

    private int findPositionByDay(String day) {

        for (int i = 0; i < listTransaction.size(); i++) {
            Transaction data = listTransaction.get(i);
            if (Convert.getDayConvertCheck(data.getDay()).equals(day)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onSelectedTransaction(Transaction transaction) {
        checkItem(transaction);
    }

    private void checkItem(Transaction transaction) {
        Thread thread = new Thread(() -> {
            try {
                BudgetApi.api.findBudget(transaction.getTransactionId()).enqueue(new Callback<Budget>() {
                    @Override
                    public void onResponse(Call<Budget> call, Response<Budget> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            showAlertDialog(response.body());
                        } else {
                            Intent intent = new Intent(getActivity(), UpdateTransactionActivity.class);
                            intent.putExtra("id", transaction.getTransactionId());
                            intent.putExtra("day", transaction.getDay());
                            intent.putExtra("note", transaction.getNote());
                            intent.putExtra("price", transaction.getPrice());
                            intent.putExtra("categoryId", transaction.getCategory().getId());
                            intent.putExtra("categoryType", transaction.getCategory().getType());
                            activityResultLauncher.launch(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Budget> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

    private void showAlertDialog(Budget budget) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận sửa");
        builder.setMessage("Đây là chi phí cố định, bạn có muốn chỉnh sửa không?");
        builder.setPositiveButton("OK", (dialog, which) -> nav(budget));
        builder.setNegativeButton("BỎ QUA", null);
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void nav(Budget budget) {
        Intent intent = new Intent(getActivity(), ThemSuaActivity.class);
        intent.putExtra(ThemSuaActivity.DATA_BUDGET, budget);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMonthView();
    }
}