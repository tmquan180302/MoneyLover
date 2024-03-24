package com.poly.moneylover.ui;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;
import java.util.Locale;

import retrofit2.HttpException;
import retrofit2.Response;

public class CalendarFragment extends Fragment {
    private TextView search;
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
    CalendarAdapter adapter1;
    private ArrayList<Dto_item> arrayList = new ArrayList<>();

    private Calendar currentDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());


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


                        // Lọc danh sách giao dịch để chỉ chứa các giao dịch có userId tương tự với userId mà người dùng đã nhập
                        for (Transaction transaction : allTransactions) {
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
                        }
                        int difference = sumType1 - sumType0;
                        tong.setText(Convert.FormatNumber(difference)+"đ");
                        System.out.println("Hiệu giữa tổng các giao dịch có type = 0 và type = 1: " + difference);

                        // In tổng các giao dịch có type = 0 và type = 1
                        System.out.println("Tổng các giao dịch có type = 0: " + sumType0);
                        System.out.println("Tổng các giao dịch có type = 1: " + sumType1);

                        requireActivity().runOnUiThread(() -> {
                            adapter.setData(filteredTransactions);
                        });

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