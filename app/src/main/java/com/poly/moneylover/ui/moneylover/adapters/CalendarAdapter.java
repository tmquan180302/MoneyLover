package com.poly.moneylover.ui.moneylover.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.models.Dto_item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


    private Map<String, Long> sumPriceType1ByDate;

    public CalendarAdapter(Context context, ArrayList<Date> days, Calendar currentDate, Map<String, Long> sumPriceType1ByDate) {
        super(context, R.layout.calendar_day, days);
        this.currentDate = currentDate;
        this.sumPriceType1ByDate = sumPriceType1ByDate;
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
    private int selectedPosition = -1; // Thêm biến để lưu vị trí của ngày được chọn

    public void setSelectedPosition(int position) {
        selectedPosition = position; // Phương thức để cập nhật vị trí của ngày được chọn
        notifyDataSetChanged(); // Thông báo cho adapter biết dữ liệu đã thay đổi
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
//// Lấy đối tượng SharedPreferences
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE);
//
//// Khởi tạo Gson để chuyển đổi từ JSON sang đối tượng
//        Gson gson = new Gson();
//
//// Lấy số lượng cặp đã lưu trong SharedPreferences
//        int pairCount = sharedPreferences.getAll().size();
//
//// Tạo danh sách để lưu trữ các cặp được nạp từ SharedPreferences
//        List<SumPriceAndDate> loadedSumPriceAndDateList = new ArrayList<>();
//
//// Duyệt qua tất cả các cặp được lưu trong SharedPreferences
//        for (int i = 0; i < pairCount; i++) {
//            // Lấy chuỗi JSON tương ứng với cặp thứ i
//            String json = sharedPreferences.getString("pair_" + i, null);
//
//            // Nếu chuỗi JSON không rỗng
//            if (json != null) {
//                // Chuyển đổi chuỗi JSON thành đối tượng SumPriceAndDate
//                SumPriceAndDate pair = gson.fromJson(json, SumPriceAndDate.class);
//
//                // Thêm cặp vào danh sách
//                loadedSumPriceAndDateList.add(pair);
//            }
//        }
//
//// Bây giờ bạn có thể sử dụng danh sách `loadedSumPriceAndDateList` để sử dụng dữ liệu đã nạp từ SharedPreferences
//// Ví dụ: duyệt qua danh sách và hiển thị các cặp đã nạp
//        for (SumPriceAndDate pair : loadedSumPriceAndDateList) {
//            System.out.println("Date Key: " + pair.dateKey + ", SumPriceType0: " + pair.sumPriceType0);
//        }
//
//        boolean foundSpecificDate = false; // Biến đánh dấu xem có ngày cụ thể nào trong danh sách không
//        String specificDate = "04/04/2024"; // Ngày cụ thể bạn muốn kiểm tra
//
//        for (SumPriceAndDate pair : loadedSumPriceAndDateList) {
//            System.out.println("Date Key: " + pair.dateKey + ", SumPriceType0: " + pair.sumPriceType0);
//
//            // Kiểm tra nếu ngày trong cặp hiện tại bằng với ngày cụ thể bạn muốn kiểm tra
//            if (pair.dateKey.equals(specificDate)) {
//                foundSpecificDate = true; // Đánh dấu rằng đã tìm thấy ngày cụ thể
//                break; // Thoát khỏi vòng lặp vì đã tìm thấy ngày cụ thể
//            }
//        }
//
//// Kiểm tra kết quả sau khi duyệt qua toàn bộ danh sách
//        if (foundSpecificDate) {
//            // Nếu có ngày cụ thể trong danh sách
//            khoanchi.setText("1");
//        } else {
//            // Nếu không có ngày cụ thể trong danh sách
//            // Ở đây bạn có thể làm gì đó nếu không tìm thấy ngày cụ thể, ví dụ: khoanchi.setText("0");
//        }

        return view;
    }

    public void loadData() {



   }


}

