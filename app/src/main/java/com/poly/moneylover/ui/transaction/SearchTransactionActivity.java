package com.poly.moneylover.ui.transaction;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.TransactionAdapter;
import com.poly.moneylover.interfaces.ItemOnclickTransaction;
import com.poly.moneylover.models.Budget;
import com.poly.moneylover.models.Request.SearchReq;
import com.poly.moneylover.models.Response.ResSearchTransaction;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.BudgetApi;
import com.poly.moneylover.ui.ThuChiCoDinh.ThemSuaActivity;
import com.poly.moneylover.utils.Convert;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTransactionActivity extends AppCompatActivity implements ItemOnclickTransaction {

    private ImageButton imbBack;
    private EditText edtSearch;
    private TextView tvRevenue, tvExpense, tvTotal;
    private RecyclerView rcvTransaction;
    private ArrayList<Transaction> list;
    private TransactionAdapter adapter;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_transaction);
        initView();
        back();
        initRecycleView();
        searchAction();
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    initRecycleView();
                }
            });

    private void back() {
        imbBack.setOnClickListener(v -> finish());
    }

    private void searchAction() {

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Xử lý tìm kiếm ở đây
                    String searchQuery = edtSearch.getText().toString();
                    searchTransaction(searchQuery);

                    // Ẩn bàn phím
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

                    return true; // Đánh dấu sự kiện đã được xử lý
                }
                return false;
            }
        });
    }

    private void searchTransaction(String searchText) {
        Thread thread = new Thread(() -> {
            try {
                SearchReq req = new SearchReq(searchText);
                BudgetApi.api.searchTransaction(req).enqueue(new Callback<ResSearchTransaction>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ResSearchTransaction> call, Response<ResSearchTransaction> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            tvExpense.setText(Convert.FormatNumber(response.body().getExpense()));
                            tvRevenue.setText(Convert.FormatNumber(response.body().getRevenue()));
                            tvTotal.setText(Convert.FormatNumber(response.body().getTotal()));

                            list.clear();
                            list.addAll(response.body().getTransactions());
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResSearchTransaction> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new TransactionAdapter(list, getApplicationContext(), this);
        rcvTransaction.setAdapter(adapter);
    }

    private void initView() {
        imbBack = findViewById(R.id.imb_back);
        edtSearch = findViewById(R.id.edt_Search);
        tvRevenue = findViewById(R.id.tv_revenue);
        tvExpense = findViewById(R.id.tv_expense);
        tvTotal = findViewById(R.id.tv_total);
        rcvTransaction = findViewById(R.id.rcv_transaction);

    }

    @Override
    public void onSelectedTransaction(Transaction transaction) {
        checkItem(transaction);
    }

    private void checkItem(Transaction transaction) {
        Thread thread = new Thread(() -> {
            try {
                BudgetApi.api.findBudget(transaction.getTransactionId()).enqueue(new Callback<Budget>() {
                    @Override
                    public void onResponse(Call<Budget> call, Response<Budget> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            showAlertDialog(response.body());
                        } else {
                            Intent intent = new Intent(getApplicationContext(), UpdateTransactionActivity.class);
                            intent.putExtra("id", transaction.getTransactionId());
                            intent.putExtra("day", transaction.getDay());
                            intent.putExtra("note", transaction.getNote());
                            intent.putExtra("price", transaction.getPrice());
                            intent.putExtra("categoryId", transaction.getCategory().getId());
                            intent.putExtra("categoryType", transaction.getCategory().getType());
                            activityResultLauncher.launch(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Budget> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

    private void showAlertDialog(Budget budget) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận sửa");
        builder.setMessage("Đây là chi phí cố định, bạn có muốn chỉnh sửa không?");
        builder.setPositiveButton("OK", (dialog, which) -> nav(budget));
        builder.setNegativeButton("BỎ QUA", null);
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void nav(Budget budget) {
        Intent intent = new Intent(getApplicationContext(), ThemSuaActivity.class);
        intent.putExtra(ThemSuaActivity.DATA_BUDGET, budget);
        activityResultLauncher.launch(intent);
    }
}