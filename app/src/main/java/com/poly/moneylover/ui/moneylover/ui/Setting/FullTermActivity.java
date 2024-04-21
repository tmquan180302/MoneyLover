package com.poly.moneylover.ui.moneylover.ui.Setting;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.models.Response.Report;
import com.poly.moneylover.ui.moneylover.network.ReportApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullTermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_term);
        getReportData();
    }

    private void getReportData() {

        Call<Report> call = ReportApi.api.getAllTimeReport();
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (response.isSuccessful()) {
                    Report report = response.body();
                    // Xử lý dữ liệu report ở đây
                     TextView txtrevenue = findViewById(R.id.revenue);
                     TextView txtexpense = findViewById(R.id.expense);
                     TextView txttotal = findViewById(R.id.total);
                     TextView txtbalance = findViewById(R.id.balance);
                     TextView txtcumulation = findViewById(R.id.cumulation);

                    double revenue = report.getRevenue();
                    double expense = report.getExpense();
                    double total = report.getTotal();
                    double balance = (int) report.getBalance();
                    double cumulation = report.getCumulation();

//                    Log.d("data","data " +report);
//                    double initialBalance = InitialBalanceSingleton.getInstance().getInitialBalance();
//                    txtbalance.setText(String.valueOf(initialBalance)); //so du ban dau

                    txtrevenue.setText(String.valueOf(revenue));
                    txtexpense.setText(String.valueOf(expense));
                    txttotal.setText(String.valueOf(total));
                    txtbalance.setText(String.valueOf(balance));

                    txtcumulation.setText(String.valueOf(total + balance));

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

}