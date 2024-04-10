package com.poly.moneylover.ui.transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.Adapter_list;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.utils.Convert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ShowCalenderActivity extends AppCompatActivity {
String selected;
    private ImageButton btnPrev;
    private TextView title;
    private Calendar currentDate = Calendar.getInstance();
    private ImageButton btnNext;
    private TextView thunhap;
    private TextView chitieu;
    private TextView tong;
    private RecyclerView recList;
    Adapter_list adapter;
    private ArrayList<Transaction> arrayList = new ArrayList<>();
    EditText textsearch;
    int sumType0 = 0; // Biến để tính tổng các giao dịch có type = 0
    int sumType1 = 0; // Biến để tính tổng các giao dịch có type = 1
    Map<String, Long> sumPriceType1ByDate = new HashMap<>();
    // Tạo một Map để lưu trữ tổng price cho mỗi ngày với type = 0
    Map<String, Long> sumPriceType0ByDate = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_calender);
        getdata();

    }
    private void getdata() {


        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        title = (TextView) findViewById(R.id.title);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        thunhap = (TextView) findViewById(R.id.thunhap);
        chitieu = (TextView) findViewById(R.id.chitieu);
        tong = (TextView) findViewById(R.id.tong);
        recList = (RecyclerView) findViewById(R.id.rec_list);
        textsearch = findViewById(R.id.textsearch   );
        ImageView back = findViewById(R.id.back  );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new Adapter_list(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recList.setLayoutManager(linearLayoutManager2);



        // Nhận Intent đã gửi
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                selected = bundle.getString("Selected Date");
                intent.putExtras(bundle);
                title.setText(selected);

            }



        }


        textsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }
    private void getListTransaction() {
        Call<List<Transaction>> call = TransactionApi.api.getListTransaction();
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        List<Transaction> allTransactions = response.body();
                        List<Transaction> filteredTransactions = new ArrayList<>();
                        arrayList.clear();
                        // Lọc danh sách giao dịch để chỉ chứa các giao dịch có userId tương tự với userId mà người dùng đã nhập
                        for (Transaction transaction : allTransactions) {
                            Transaction yourData = new Transaction();
                            yourData.setDay(transaction.getDay());
                            String dateString = yourData.convertDayToDateString();
                            if (dateString.equals(selected)) {
                                filteredTransactions.add(transaction);
                                if (transaction.getCategory().getType() == 0) {
                                    sumType0 += transaction.getPrice();
                                    chitieu.setText(Convert.FormatNumber(sumType0)+"đ");
                                } else if (transaction.getCategory().getType() == 1) {
                                    sumType1 += transaction.getPrice();
                                    thunhap.setText(Convert.FormatNumber(sumType1)+"đ");

                                }

                                    int difference = sumType1 - sumType0;
                                    tong.setText(Convert.FormatNumber(difference)+"đ");

                                    arrayList.addAll(response.body());
                                    adapter.setData(arrayList);
                                    recList.setAdapter(adapter);
                                    // Sắp xếp danh sách theo trường ngày giảm dần
                                    Collections.sort(filteredTransactions, (transaction1, transaction2) -> {
                                        long date1 = transaction1.getDay();
                                        long date2 = transaction2.getDay();
                                        return Long.compare(date2, date1); // So sánh ngày giảm dần
                                    });


                                }
                                // Hiển thị văn bản "Không có dữ liệu" nếu danh sách rỗng
                                if (filteredTransactions.isEmpty()) {
                                    chitieu.setText("0đ");
                                    thunhap.setText("0đ");
                                    tong.setText("0đ");
                                   // khongcodulieu.setVisibility(View.VISIBLE);
                                } else {
                                   // khongcodulieu.setVisibility(View.INVISIBLE);
                                }
                            }


                            // Kiểm tra nếu type = 1 thì cộng vào tổng của type 1, ngược lại thì bỏ qua

                        adapter.setData(filteredTransactions);
                    });
                }
//                if (response.isSuccessful()) {
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTimeInMillis(transaction.getDay());
//                    int transactionMonth = cal.get(Calendar.MONTH);
//                    int transactionYear = cal.get(Calendar.YEAR);
//
//
//                    arrayList.addAll(response.body());
//                    adapter.setData(arrayList);
//                    recList.setAdapter(adapter);
//                } else {
//                    Toast.makeText(ShowCalenderActivity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(ShowCalenderActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        List<Transaction> filteredList = new ArrayList<>();
        for (Transaction transaction : arrayList) {
            if (transaction.getCategory().getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(transaction);

            }
        }
        adapter.setData((ArrayList<Transaction>) filteredList);
    }
    @Override
    public void onResume() {
        super.onResume();
        getListTransaction();
    }
}