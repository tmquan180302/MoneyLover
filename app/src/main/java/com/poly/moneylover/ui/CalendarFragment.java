package com.poly.moneylover.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.poly.moneylover.adapters.Adapter_list;
import com.poly.moneylover.adapters.CalendarAdapter;
import com.poly.moneylover.models.Dto_item;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.ui.transaction.SearchActivity;
import com.poly.moneylover.utils.Convert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


    private ArrayList<Dto_item> arrayList = new ArrayList<>();

    private Calendar currentDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    // Phương thức để lưu trữ dữ liệu vào SharedPreferences

    // Sử dụng phương thức saveData() để lưu dữ liệu


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

        title = view.findViewById(R.id.title);

        search = (TextView) view.findViewById(R.id.search);
        thunhap = (TextView) view.findViewById(R.id.thunhap);
        chitieu = (TextView) view.findViewById(R.id.chitieu);
        tong = (TextView) view.findViewById(R.id.tong);
        sodudauki = (TextView) view.findViewById(R.id.sodudauki);
        soduhientai = (TextView) view.findViewById(R.id.soduhientai);
        recList = (RecyclerView) view.findViewById(R.id.rec_list);

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

                // Hiển thị Toast khi ngày được chọn
                Toast.makeText(getContext(), "Selected Date: " + formattedDate, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getListTransaction() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Transaction>> response = TransactionApi.api.getListTransaction().execute();
                if (response.isSuccessful()) {
                    requireActivity().runOnUiThread(() -> {
                        List<Transaction> allTransactions = response.body();
                        List<Transaction> filteredTransactions = new ArrayList<>();

                        // Tạo một Map để lưu trữ tổng price cho mỗi ngày với type = 1
                        Map<String, Long> sumPriceType1ByDate = new HashMap<>();
                        // Tạo một Map để lưu trữ tổng price cho mỗi ngày với type = 0
                        Map<String, Long> sumPriceType0ByDate = new HashMap<>();
                        // Lọc danh sách giao dịch để chỉ chứa các giao dịch có userId tương tự với userId mà người dùng đã nhập
                        for (Transaction transaction : allTransactions) {
                            // Lấy ngày của giao dịch
                            Transaction yourData = new Transaction();
                            yourData.setDay(transaction.getDay()); // Gán giá trị millis cho trường "day"
                            // Chuyển đổi millis sang ngày tháng năm bằng phương thức convertDayToDateString()
                            String dateString = yourData.convertDayToDateString();
                            // Kiểm tra xem giao dịch có type = 0 không

                            if (transaction.getUserId().equals(transaction.getUserId())) {
                                filteredTransactions.add(transaction);
                                // Kiểm tra nếu type = 0 thì cộng vào tổng của type 0, ngược lại thì cộng vào tổng của type 1
                                if (transaction.getCategory().getType() == 0) {
                                    sumType0 += transaction.getPrice();
                                    chitieu.setText(Convert.FormatNumber(sumType0)+"đ");
                                } else if (transaction.getCategory().getType() == 1) {
                                    sumType1 += transaction.getPrice();
                                    thunhap.setText(Convert.FormatNumber(sumType1)+"đ");
                                }
                            }

                        int difference = sumType1 - sumType0;
                        tong.setText(Convert.FormatNumber(difference)+"đ");
                        System.out.println("Hiệu giữa tổng các giao dịch có type = 0 và type = 1: " + difference);

                        // In tổng các giao dịch có type = 0 và type = 1
                        System.out.println("Tổng các giao dịch có type = 0: " + sumType0);
                        System.out.println("Tổng các giao dịch có type = 1: " + sumType1);

                        // Lặp qua danh sách giao dịch để tính tổng price có type = 1 cho mỗi ngày

                            // Kiểm tra nếu type = 1 thì cộng vào tổng của type 1, ngược lại thì bỏ qua
                            if (transaction.getCategory().getType() == 1) {


                                // Kiểm tra xem ngày đã tồn tại trong Map chưa
                                if (sumPriceType1ByDate.containsKey(dateString)) {
                                    // Nếu tồn tại, cộng giá trị price của giao dịch vào tổng của ngày đó
                                    long currentSumPriceType1 = sumPriceType1ByDate.get(dateString);
                                    sumPriceType1ByDate.put(dateString, currentSumPriceType1 + transaction.getPrice());
                                } else {
                                    // Nếu không tồn tại, thêm một cặp key-value mới vào Map với giá trị giao dịch là tổng của ngày đó
                                    sumPriceType1ByDate.put(dateString, transaction.getPrice());
                                }
                            }
                            for (String dateKey : sumPriceType0ByDate.keySet()) {
                                long sumPriceType0 = sumPriceType0ByDate.get(dateKey);
                                System.out.println("Ngày " + dateKey + ": sumPriceType0 = " + sumPriceType0);

                            }
                            if (transaction.getCategory().getType() == 0) {


                                // Kiểm tra xem ngày đã tồn tại trong Map chưa
                                if (sumPriceType0ByDate.containsKey(dateString)) {
                                    // Nếu tồn tại, cộng giá trị price của giao dịch vào tổng của ngày đó
                                    long currentSumPriceType0 = sumPriceType0ByDate.get(dateString);
                                    sumPriceType0ByDate.put(dateString, currentSumPriceType0 + transaction.getPrice());
                                } else {
                                    // Nếu không tồn tại, thêm một cặp key-value mới vào Map với giá trị giao dịch là tổng của ngày đó
                                    sumPriceType0ByDate.put(dateString, transaction.getPrice());
                                }
                            }



                            for (String dateKey : sumPriceType1ByDate.keySet()) {
                                long sumPriceType1 = sumPriceType1ByDate.get(dateKey);
                                System.out.println("Ngày " + dateKey + ": sumPriceType1 = " + sumPriceType1);

                            }

                        // In tổng các giá trị price có type = 0 cho mỗi ngày ra màn hình

                        }

                        // Cập nhật dữ liệu lên RecyclerView
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


    private void updateCalendar() {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (cells.size() < 42) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Định dạng số tháng và năm
        SimpleDateFormat numberFormat = new SimpleDateFormat("MM/yyyy");
        String monthAndYear = numberFormat.format(currentDate.getTime());

        // Hiển thị số tháng và năm trong title
        title.setText(monthAndYear);

        CalendarAdapter calendarAdapter = new CalendarAdapter(getContext(), cells, currentDate);
        calendarGrid.setAdapter(calendarAdapter);
    }



    @Override
    public void onResume() {
        super.onResume();
       getListTransaction();
    }
}