package com.poly.moneylover.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.ItemOnclickCalendar;
import com.poly.moneylover.models.CalendarApp;
import com.poly.moneylover.models.Dto_item;
import com.poly.moneylover.utils.CalendarUtils;
import com.poly.moneylover.utils.Convert;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private List<CalendarApp> list;

    private ItemOnclickCalendar onclickCalendar;

    public CalendarAdapter(List<CalendarApp> list, ItemOnclickCalendar onclickCalendar) {
        this.list = list;
        this.onclickCalendar = onclickCalendar;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (list.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.2);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarApp calendarApp = list.get(position);
        holder.dayOfMonth.setText(String.valueOf(calendarApp.getDate().getDayOfMonth()));
        holder.tvExpense.setText(calendarApp.getExpense() == 0 ? "" : Convert.formatNumberCurrent(String.valueOf(calendarApp.getExpense())));
        holder.tvRevenue.setText(calendarApp.getRevenue() == 0 ? "" : Convert.formatNumberCurrent(String.valueOf(calendarApp.getRevenue())));

        if (calendarApp.getDate().equals(CalendarUtils.selectedDate))
            holder.parentView.setBackgroundColor(Color.LTGRAY);

        if (calendarApp.getDate().getMonth().equals(CalendarUtils.selectedDate.getMonth()))
            holder.dayOfMonth.setTextColor(Color.BLACK);
        else
            holder.dayOfMonth.setTextColor(Color.LTGRAY);

        holder.itemView.setOnClickListener(v -> {
            onclickCalendar.onSelectedCalendar(calendarApp);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {

        View parentView;
        private TextView dayOfMonth, tvExpense, tvRevenue;


        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            parentView = itemView.findViewById(R.id.parentView);
            dayOfMonth = itemView.findViewById(R.id.tv_dayInMonthCell);
            tvExpense = itemView.findViewById(R.id.tv_expenseCell);
            tvRevenue = itemView.findViewById(R.id.tv_revenueCell);

        }
    }


}