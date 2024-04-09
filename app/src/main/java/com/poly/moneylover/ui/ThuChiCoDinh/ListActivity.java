package com.poly.moneylover.ui.ThuChiCoDinh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.poly.moneylover.adapters.ItemBudgetAdapter;
import com.poly.moneylover.databinding.ActivityListBinding;
import com.poly.moneylover.interfaces.ItemOnClickBudget;
import com.poly.moneylover.models.Budget;
import com.poly.moneylover.network.BudgetApi;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements ItemOnClickBudget {
    private ActivityListBinding binding;
    private ItemBudgetAdapter adapter;
    private boolean isLongClick = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClickAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData(){
            binding.recList.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ItemBudgetAdapter(this, ListActivity.this);
            adapter.setOnLongClickListener(new ItemBudgetAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(Budget budget) {
                showAlertDialog(budget);
            }
        });
            binding.recList.setAdapter(adapter);
            getListBudget();
    }

    public void onClickAction(){
        binding.imgBack.setOnClickListener(v->{
            finish();
        });
        binding.themds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, ThemSuaActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void delete(String idBudget) {
        Thread thread = new Thread(() -> {
            try {
                Response<String> call = BudgetApi.api.delete(idBudget).execute();
                if (call.isSuccessful() && call.code() == 200) {
                    getListBudget();
                    runOnUiThread(() -> Toast.makeText(ListActivity.this, call.body(), Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(ListActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show());
                }
            } catch (HttpException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Mất kết nối với server", Toast.LENGTH_SHORT).show());
            }
        });
        thread.start();
    }

    public void getListBudget(){
        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<List<Budget>> data = BudgetApi.api.getList().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) runOnUiThread(() -> {
                        adapter.setList(data.body());
                    });
                }
            } catch (HttpException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Mất kết nối với server", Toast.LENGTH_SHORT).show());
            }
        });
        thread.start();
    }

    @Override
    public void onSelectdBudgetCategory(Budget budget) {
            Intent intent = new Intent(this, ThemSuaActivity.class);
            intent.putExtra(ThemSuaActivity.DATA_BUDGET, budget);
            startActivity(intent);
    }
    private void showAlertDialog(Budget budget) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setPositiveButton("OK", (dialog, which) -> delete(budget.get_id()));
        builder.setNegativeButton("BỎ QUA", null);
        Dialog dialog = builder.create();
        dialog.show();
    }
}