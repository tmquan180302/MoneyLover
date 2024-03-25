package com.poly.moneylover.ui.SoDuBanDau;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.models.Balance;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Response.BalanceRequestBody;
import com.poly.moneylover.models.Response.InitialBalanceSingleton;
import com.poly.moneylover.network.ApiClient;
import com.poly.moneylover.network.CategoryApi;
import com.poly.moneylover.network.SoDuBanDauApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class SoDuBanDauActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_du_ban_dau);
        RelativeLayout khac0 = findViewById(R.id.khac0);
        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v ->{
            finish();
        });
        khac0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
        //fetchBalanceData();
        getsodubandau();
    }
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText editTextAmount = dialogView.findViewById(R.id.editTextAmount);

        // Lấy số tiền hiện tại từ TextView sodubandau
        TextView textViewPrice = findViewById(R.id.sodubandau);
        String currentPrice = textViewPrice.getText().toString();

        // Set giá trị của EditText là số tiền hiện tại
        editTextAmount.setText(currentPrice);

        builder.setView(dialogView);
        builder.setTitle("Số dư ban đầu");
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String amount = editTextAmount.getText().toString();
                double price = Double.parseDouble(amount);
                String number = "^[0-9]*$";
                    createSoDuBanDau(price);
            }
        });
        builder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void createSoDuBanDau(double price) {
        Thread thread = new Thread(() -> {
            try {
                BalanceRequestBody requestBody = new BalanceRequestBody(price);
                Response<String> response = SoDuBanDauApi.api.createSoDuBanDau(requestBody).execute();
                if (response.isSuccessful()) {
                    String message = response.body(); // Thông báo từ server
                    runOnUiThread(() -> {
                        // Hiển thị thông báo thành công
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        // Sau khi tạo số dư ban đầu thành công, cập nhật lại số dư hiển thị trên giao diện
                        getsodubandau();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Lỗi khi tạo số dư ban đầu", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                });
            }
        });
        thread.start();
    }

    private void getsodubandau() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Balance>> data = SoDuBanDauApi.api.getSoDuBanDau().execute();
                if (data.isSuccessful()) {
                    List<Balance> balances = data.body();
                    if (balances != null && !balances.isEmpty()) {
                        Balance balance = balances.get(0);
                        int price = balance.getPrice();

                        runOnUiThread(() -> {
                            TextView textViewPrice = findViewById(R.id.sodubandau);
                            textViewPrice.setText(String.valueOf(price));
                            InitialBalanceSingleton.getInstance().setInitialBalance(price);
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
                );
            }
        });
        thread.start();
    }


}


