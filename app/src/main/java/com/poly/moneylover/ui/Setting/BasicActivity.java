package com.poly.moneylover.ui.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.ui.OtherCategories.ListActivity;
import com.poly.moneylover.ui.SoDuBanDau.SoDuBanDauActivity;
import com.poly.moneylover.ui.ThuChiCoDinh.ThemSuaActivity;
import com.poly.moneylover.ui.category.EditActivity;

public class BasicActivity extends AppCompatActivity implements ItemOnclick {
    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        // Lấy Intent đã gửi lúc khởi tạo Activity
        Intent receivedIntent = getIntent();

        // Trích xuất giá trị type từ Intent
        int type = receivedIntent.getIntExtra("type", 0); // Nếu không tìm thấy "type", mặc định là 0

        RelativeLayout khac0 = findViewById(R.id.khac0);
        RelativeLayout khac1 = findViewById(R.id.khac1);
        RelativeLayout khac2 = findViewById(R.id.khac2);
        khac0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasicActivity.this, com.poly.moneylover.ui.ThuChiCoDinh.ListActivity.class);
                startActivity(intent);
            }
        });
        khac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasicActivity.this, ListActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
        khac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasicActivity.this, SoDuBanDauActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onSelectedCategory(Category category) {
        this.category = category;
    }

    @Override
    public void editItem() {
        Intent intent = new Intent(BasicActivity.this, EditActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }
}