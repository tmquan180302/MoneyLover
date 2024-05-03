package com.poly.moneylover.ui.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.models.Response.InitialBalanceSingleton;
import com.poly.moneylover.models.Response.Report;
import com.poly.moneylover.network.ApiClient;
import com.poly.moneylover.network.ReportApi;
import com.poly.moneylover.utils.Convert;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullTermActivity extends AppCompatActivity {
    TextView tvRevenue, tvExpense, tvTotal, tvBalance, tvCumulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_term);
        ImageView imgBack = findViewById(R.id.imgBack);
        initView();
        imgBack.setOnClickListener(v -> {
            finish();
        });
        getReportData();
    }

    private void initView() {

        tvRevenue = findViewById(R.id.revenue);
        tvExpense = findViewById(R.id.expense);
        tvTotal = findViewById(R.id.total);
        tvBalance = findViewById(R.id.balance);
        tvCumulation = findViewById(R.id.cumulation);
    }

    private void getReportData() {

        Call<Report> call = ReportApi.api.getAllTimeReport();
        call.enqueue(new Callback<Report>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (response.isSuccessful() && response.body() != null) {

                    tvRevenue.setText((Convert.convertNumber(response.body().getRevenue())) + "đ");
                    tvExpense.setText((Convert.convertNumber(response.body().getExpense())) + " đ");
                    tvTotal.setText((Convert.convertNumber(response.body().getTotal())) + " đ");
                    tvBalance.setText((Convert.convertNumber(response.body().getBalance())) + " đ");
                    tvCumulation.setText((Convert.convertNumber(response.body().getCumulation())) + " đ");

                } else {
                    Toast.makeText(FullTermActivity.this, "Chưa có data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(FullTermActivity.this, "Looix", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatMoney(double amount) {
        if (amount == 0) {
            return "0";
        } else {
            return String.format("%,.0f", amount);
        }
    }

}