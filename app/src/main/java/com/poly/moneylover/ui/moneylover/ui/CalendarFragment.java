package com.poly.moneylover.ui.moneylover.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.adapters.Adapter_list;
import com.poly.moneylover.ui.moneylover.adapters.CalendarAdapter;
import com.poly.moneylover.ui.moneylover.models.Dto_item;
import com.poly.moneylover.ui.moneylover.models.Transaction;
import com.poly.moneylover.ui.moneylover.network.TransactionApi;
import com.poly.moneylover.ui.moneylover.ui.transaction.SearchActivity;
import com.poly.moneylover.ui.moneylover.ui.transaction.ShowCalenderActivity;
import com.poly.moneylover.ui.moneylover.utils.Convert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.HttpException;
import retrofit2.Response;

public class CalendarFragment extends Fragment {
    private TextView search;


    // Phương thức để ghi dữ liệu

    int sumType0 = 0; // Biến để tính tổng các giao dịch có type = 0
    int sumType1 = 0; // Biến để tính tổng các giao dịch có type = 1
    private ImageButton btnPrev;
    ArrayList<Date> cells;
    long sumPriceType0;
    private TextView title;
    private ImageButton btnNext;
    private GridView calendarGrid;
    private TextView thunhap;
    private TextView chitieu;
    private TextView tong;
    private TextView sodudauki;
    private TextView soduhientai;
    private RecyclerView recList;

    Adapter_list adapter;

    // Tạo một Map để lưu trữ tổng price cho mỗi ngày với type = 1
    Map<String, Long> sumPriceType1ByDate = new HashMap<>();
    // Tạo một Map để lưu trữ tổng price cho mỗi ngày với type = 0
    Map<String, Long> sumPriceType0ByDate = new HashMap<>();
    private ArrayList<Dto_item> arrayList = new ArrayList<>();

    private Calendar currentDate = Calendar.getInstance();
    TextView khongcodulieu;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    // Phương thức để lưu trữ dữ liệu vào SharedPreferences

    // Sử dụng phương thức saveData() để lưu dữ liệu
    private String myData;
Calendar    calendar = Calendar.getInstance();

    // Phương thức để lấy dữ liệu
    public String getData() {
        return myData;
    }

    // Phương thức để cập nhật dữ liệu
    public void setData(String data) {
        myData = data;
        // Gọi phương thức để thông báo rằng dữ liệu đã thay đổi (nếu cần)
        notifyDataChanged();
    }

    // Phương thức thông báo rằng dữ liệu đã thay đổi (nếu cần)
    private void notifyDataChanged() {
        // Thông báo cho bất kỳ lớp lắng nghe nào về sự thay đổi dữ liệu
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        calendarGrid = view.findViewById(R.id.calendarGrid);
        btnPrev = view.findViewById(R.id.btnPrev);
        btnNext = view.findViewById(R.id.btnNext);
      khongcodulieu = view.findViewById(R.id.khongcodulieu);
        title = view.findViewById(R.id.title);

        search = (TextView) view.findViewById(R.id.search);
        thunhap = (TextView) view.findViewById(R.id.thunhap);
        chitieu = (TextView) view.findViewById(R.id.chitieu);
        tong = (TextView) view.findViewById(R.id.tong);

        recList = (RecyclerView) view.findViewById(R.id.rec_list);

         title.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 openDialog(    );
             }
         });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        adapter = new Adapter_list(getContext());
        //   ArrayList<Dto_item> sampleData = generateSampleData();
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recList.setLayoutManager(linearLayoutManager2);
        recList.setAdapter(adapter);
        updateCalendar();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });
        // Thêm OnItemClickListener cho GridView
        calendarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date selectedDate = (Date) parent.getItemAtPosition(position);
                SimpleDateFormat selectedDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = selectedDateFormat.format(selectedDate);
//SumBydate();
                // Hiển thị Toast khi ngày được chọn
                Toast.makeText(getContext(), "Selected Date: " + formattedDate, Toast.LENGTH_SHORT).show();
                CalendarAdapter calendarAdapter = new CalendarAdapter(getContext(), cells, currentDate);
                calendarAdapter.setSelectedPosition(position);
                Intent intent = new Intent(getContext(), ShowCalenderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Selected Date",formattedDate); // Thêm dữ liệu vào bundle
                intent.putExtras(bundle);
               getContext().startActivity(intent);
            }
        });
    }


        private void getListTransaction(int month, int year) {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Transaction>> response = TransactionApi.api.getListTransaction().execute();
                if (response.isSuccessful()) {
                    requireActivity().runOnUiThread(() -> {
                        List<Transaction> allTransactions = response.body();
                        List<Transaction> filteredTransactions = new ArrayList<>();


                        // Lọc danh sách giao dịch để chỉ chứa các giao dịch có userId tương tự với userId mà người dùng đã nhập
                        for (Transaction transaction : allTransactions) {
                            // Lấy ngày của giao dịch
                            Transaction yourData = new Transaction();
                            yourData.setDay(transaction.getDay()); // Gán giá trị millis cho trường "day"
                            // Chuyển đổi millis sang ngày tháng năm bằng phương thức convertDayToDateString()
                            String dateString = yourData.convertDayToDateString();
                            // Kiểm tra xem giao dịch có type = 0 không
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(transaction.getDay());
                            int transactionMonth = cal.get(Calendar.MONTH);
                            int transactionYear = cal.get(Calendar.YEAR);

                            if (transactionMonth == month && transactionYear == year) {
                                filteredTransactions.add(transaction);
                                // Xử lý tính tổng và các thao tác khác

                                // Kiểm tra nếu type = 0 thì cộng vào tổng của type 0, ngược lại thì cộng vào tổng của type 1
                                if (transaction.getCategory().getType() == 0) {
                                    sumType0 += transaction.getPrice();
                                    chitieu.setText(Convert.FormatNumber(sumType0)+"đ");
                                } else if (transaction.getCategory().getType() == 1) {
                                    sumType1 += transaction.getPrice();
                                    thunhap.setText(Convert.FormatNumber(sumType1)+"đ");

                                }
                                int difference = sumType1 - sumType0;
                                tong.setText(Convert.FormatNumber(difference)+"đ");
                                khongcodulieu.setVisibility(View.INVISIBLE);

                                // Sắp xếp danh sách theo trường ngày giảm dần
                                Collections.sort(filteredTransactions, (transaction1, transaction2) -> {
                                    long date1 = transaction1.getDay();
                                    long date2 = transaction2.getDay();
                                    return Long.compare(date2, date1); // So sánh ngày giảm dần
                                });


                            }
                            else {

                                // Hiển thị văn bản "Không có dữ liệu" nếu danh sách rỗng
                                if (filteredTransactions.isEmpty()) {
                                    chitieu.setText("0đ");
                                    thunhap.setText("0đ");
                                    tong.setText("0đ");
                                    khongcodulieu.setVisibility(View.VISIBLE);
                                } else {
                                    khongcodulieu.setVisibility(View.INVISIBLE);
                                }
                            }


                            // Kiểm tra nếu type = 1 thì cộng vào tổng của type 1, ngược lại thì bỏ qua
                        }
                        adapter.setData(filteredTransactions);
                    });
                }
            } catch (HttpException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), e.message(), Toast.LENGTH_SHORT).show();
                });
            } catch (IOException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
                });
            }
        });

        thread.start();
    }
    private void openDialog() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(), R.style.DialogTheme, (view, year1, month1, day1) -> {
            // Cập nhật currentDate với tháng mới
            currentDate.set(Calendar.YEAR, year1);
            currentDate.set(Calendar.MONTH, month1);
            currentDate.set(Calendar.DAY_OF_MONTH, day1);

            // Gọi phương thức updateCalendar() để cập nhật lịch
            updateCalendar();
        }, year, month, day);
        dialog.show();
    }




    private void updateCalendar() {
       cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (cells.size() < 42) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Định dạng số tháng và năm


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        String monthAndYear = dateFormat.format(currentDate.getTime());


// Tạo một đối tượng Calendar và đặt ngày là ngày đầu tháng

        calendar.setTime(currentDate.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Đặt ngày là ngày đầu tháng

// Lấy ngày đầu tháng
        Date firstDayOfMonth = calendar.getTime();

// Đặt ngày là ngày cuối tháng
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

// Lấy ngày cuối tháng
        Date lastDayOfMonth = calendar.getTime();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM");
// Định dạng ngày đầu tháng và ngày cuối tháng
        String firstAndLastDayOfMonth = dateFormat1.format(firstDayOfMonth) + " - " + dateFormat1.format(lastDayOfMonth);
// Định dạng số tháng và năm với chữ to và in đậm
        SpannableStringBuilder monthAndYearBuilder = new SpannableStringBuilder(monthAndYear);
        monthAndYearBuilder.setSpan(new RelativeSizeSpan(1.5f), 0, monthAndYear.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        monthAndYearBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, monthAndYear.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Định dạng ngày đầu tháng và ngày cuối tháng với kích thước chữ nhỏ hơn
        SpannableStringBuilder firstAndLastDayBuilder = new SpannableStringBuilder(firstAndLastDayOfMonth);
        firstAndLastDayBuilder.setSpan(new RelativeSizeSpan(1.0f), 0, firstAndLastDayOfMonth.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Kết hợp số tháng và năm với ngày đầu tháng và ngày cuối tháng và hiển thị trong title
        CharSequence combinedText = TextUtils.concat(monthAndYearBuilder, " (", firstAndLastDayBuilder, ")");
        title.setText(combinedText);
        getListTransaction(currentDate.get(Calendar.MONTH), currentDate.get(Calendar.YEAR));
        CalendarAdapter calendarAdapter = new CalendarAdapter(getContext(), cells, currentDate);
        calendarGrid.setAdapter(calendarAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        getListTransaction(currentDate.get(Calendar.MONTH), currentDate.get(Calendar.YEAR));
    }
}