package com.poly.moneylover.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.poly.moneylover.R;
import com.poly.moneylover.models.Dto_item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarAdapter extends ArrayAdapter<Date> {
    private final LayoutInflater inflater;
    private final Calendar currentDate;
    private final ArrayList<Date> days;
    private ArrayList<Dto_item> arrayList = new ArrayList<>();

    TextView khoanchi;
    public CalendarAdapter(Context context, ArrayList<Date> days, Calendar currentDate) {
        super(context, R.layout.calendar_day, days);
        this.days = days;
        this.currentDate = currentDate;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDtoItems(ArrayList<Dto_item> items) {
        this.arrayList.clear();
        this.arrayList.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the date
        Date date = getItem(position);

        // Inflate the cell
        if (view == null) {
            view = inflater.inflate(R.layout.calendar_day, parent, false);
        }

        // Get references to the views
        TextView dayTextView = view.findViewById(R.id.textViewDay);
        TextView khoanthu = view.findViewById(R.id.khoanthu);

         khoanchi = view.findViewById(R.id.khoanchi);


        // Set the text of the day
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        dayTextView.setText(sdf.format(date));
        // Get the amount for the current date


        // Display the amount on khoanchi

        // Customize the text color based on the month and day of week
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)) {
            // Current month
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                // Saturday - set text color to blue
                dayTextView.setTextColor(Color.BLUE);
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                // Sunday - set text color to red
                dayTextView.setTextColor(Color.RED);
            } else {
                // Other weekdays - set text color to black
                dayTextView.setTextColor(Color.BLACK);
            }
        } else {
            // Other months - set text color to gray
            dayTextView.setTextColor(Color.GRAY);
        }


        return view;
    }

}