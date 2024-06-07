package com.poly.moneylover.ui.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.utils.Convert;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OptionRestoreTransactionActivity extends AppCompatActivity {
    private ImageButton imbBack;
    private TextView tvTitle, tvDay, tvNote, tvType, tvPrice, tvName, tvRestore, tvDelete;
    private ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_restore_transaction);
        initView();
        back();
        getIntentData();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String day = Convert.getDayConvert((intent.getLongExtra("day", 0)));
        String note = intent.getStringExtra("note");
        int type = (intent.getIntExtra("type", 0));
        String typeS = (type == 0) ? "Loại chi" : "Loại thu";
        String price = String.valueOf(intent.getLongExtra("price", 0));
        int icon = intent.getIntExtra("icon", 0);
        int color = intent.getIntExtra("color", 0);


        tvTitle.setText(name);
        tvDay.setText(day);
        tvNote.setText(note);
        tvType.setText(typeS);
        tvPrice.setText(Convert.formatNumberCurrent(price));
        tvName.setText(name);

        imgIcon.setVisibility(View.VISIBLE);
        imgIcon.setImageResource(icon);
        imgIcon.setColorFilter(ContextCompat.getColor(this, color));

        tvRestore.setOnClickListener(v -> restore(id));

        tvDelete.setOnClickListener(v -> delete(id));
    }

    private void delete(String id) {
        TransactionApi.api.forceDelete(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(OptionRestoreTransactionActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OptionRestoreTransactionActivity.this, RestoreTransactionActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void restore(String id) {
        TransactionApi.api.restore(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(OptionRestoreTransactionActivity.this, "Khôi phục thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OptionRestoreTransactionActivity.this, RestoreTransactionActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void back() {
        imbBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        imbBack = findViewById(R.id.imb_back);
        tvTitle = findViewById(R.id.tv_title);
        tvDay = findViewById(R.id.tv_day);
        tvNote = findViewById(R.id.tv_note);
        tvType = findViewById(R.id.tv_type);
        tvPrice = findViewById(R.id.tv_price);
        tvName = findViewById(R.id.tv_name);
        tvRestore = findViewById(R.id.tv_store);
        tvDelete = findViewById(R.id.tv_delete);

        imgIcon = findViewById(R.id.img_category);
    }
}