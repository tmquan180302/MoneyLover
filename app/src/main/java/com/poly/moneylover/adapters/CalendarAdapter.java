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
import com.poly.moneylover.ui.CalendarFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CalendarAdapter extends ArrayAdapter<Date>  {
    private LayoutInflater inflater;
    private Calendar currentDate;
    private String data;


    private ArrayList<Date> days;
    private ArrayList<Dto_item> arrayList = new ArrayList<>();

    TextView khoanchi;

    Context context;


    // Khai báo mảng để lưu trữ sumPriceType1 và sumPriceType0 tương ứng với mỗi ngày
    private HashMap<String, Long> sumPriceType1Map;
    private HashMap<String, Long> sumPriceType0Map;

    public CalendarAdapter(Context context, ArrayList<Date> days, Calendar currentDate) {
        super(context, R.layout.calendar_day, days);
        this.days = days;
        // Khởi tạo các mảng
        sumPriceType1Map = new HashMap<>();
        sumPriceType0Map = new HashMap<>();
        this.currentDate = currentDate;
        this.inflater = LayoutInflater.from(context);
    }


    // Phương thức để cập nhật dữ liệu sumPriceType1
    public void updateSumPriceType1Map(HashMap<String, Long> map) {
        sumPriceType1Map.clear();
        sumPriceType1Map.putAll(map);
        notifyDataSetChanged();
    }

    // Phương thức để cập nhật dữ liệu sumPriceType0
    public void updateSumPriceType0Map(HashMap<String, Long> map) {
        sumPriceType0Map.clear();
        sumPriceType0Map.putAll(map);
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

        // Lấy ngày tương ứng
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateString = dateFormat.format(date);

        // Hiển thị giá trị sumPriceType1 và sumPriceType0 tương ứng
        if (sumPriceType1Map.containsKey(dateString)) {
            khoanthu.setText(String.valueOf(sumPriceType1Map.get(dateString)));
        } else {
            khoanthu.setText("");
        }

        if (sumPriceType0Map.containsKey(dateString)) {
            khoanchi.setText(String.valueOf(sumPriceType0Map.get(dateString)));
        } else {
            khoanchi.setText("");
        }

        return view;
    }


    public interface DataChangeListener {
        void onDataChange(String data);
    }

}
