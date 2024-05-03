package com.poly.moneylover.ui.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemTransactionAdapter;
import com.poly.moneylover.adapters.ServiceAdapter;
import com.poly.moneylover.adapters.TransactionAdapter;
import com.poly.moneylover.interfaces.ItemOnclickTransaction;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.ui.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoreTransactionActivity extends AppCompatActivity implements ItemOnclickTransaction {
    private ImageButton imgBack;
    private RecyclerView rcvTransaction;
    private TransactionAdapter adapter;
    private List<Transaction> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_transaction);
        initView();
        back();
        initRecyleView();
        getTransaction();
    }

    private void getTransaction() {
        try {
            TransactionApi.api.getListDeleted().enqueue(new Callback<List<Transaction>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        list.clear();
                        list.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Transaction>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void back() {
        imgBack.setOnClickListener(v -> finish());
    }

    private void initRecyleView() {
        list = new ArrayList<>();
        rcvTransaction.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TransactionAdapter(list,getApplicationContext() ,this);
        rcvTransaction.setAdapter(adapter);
    }

    private void initView() {
        imgBack = findViewById(R.id.imb_back);
        rcvTransaction = findViewById(R.id.rcv_transaction);
    }

    @Override
    public void onSelectedTransaction(Transaction transaction) {

        Intent intent = new Intent(RestoreTransactionActivity.this, OptionRestoreTransactionActivity.class);
        intent.putExtra("id", transaction.getTransactionId());
        intent.putExtra("day", transaction.getDay());
        intent.putExtra("note", transaction.getNote());
        intent.putExtra("type", transaction.getCategory().getType());
        intent.putExtra("price", transaction.getPrice());
        intent.putExtra("name", transaction.getCategory().getName());
        intent.putExtra("icon", transaction.getCategory().getIcon());
        intent.putExtra("color", transaction.getCategory().getColor());
        startActivity(intent);
    }
}