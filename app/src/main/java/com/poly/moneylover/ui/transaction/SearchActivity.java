package com.poly.moneylover.ui.transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.Adapter_list;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.TransactionApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private EditText textsearch;
    ImageView back;
    private RecyclerView recList;
    private ArrayList<Transaction> arrayList = new ArrayList<>();
    private Adapter_list adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textsearch = findViewById(R.id.textsearch);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recList = findViewById(R.id.rec_list);
        adapter = new Adapter_list(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recList.setLayoutManager(linearLayoutManager2);
        getListTransaction();
       recList.setVisibility(View.INVISIBLE);

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



                    arrayList.addAll(response.body());
                    adapter.setData(arrayList);
                    recList.setAdapter(adapter);
                } else {
                    Toast.makeText(SearchActivity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        List<Transaction> filteredList = new ArrayList<>();
        for (Transaction transaction : arrayList) {
            if (transaction.getCategory().getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(transaction);
                recList.setVisibility(View.VISIBLE);
            }
        }
        adapter.setData((ArrayList<Transaction>) filteredList);
    }
}
