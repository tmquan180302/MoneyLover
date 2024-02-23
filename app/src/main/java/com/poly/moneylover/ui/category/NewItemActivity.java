package com.poly.moneylover.ui.category;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ColorAdapter;
import com.poly.moneylover.adapters.IconAdapter;
import com.poly.moneylover.interfaces.ColorOnClick;
import com.poly.moneylover.models.Item;
import com.poly.moneylover.utils.EditTextUtils;

import java.util.ArrayList;
import java.util.List;

public class NewItemActivity extends AppCompatActivity implements ColorOnClick {
    private RecyclerView rcvIcon, rcvColor;
    private IconAdapter iconAdapter;
    private ColorAdapter colorAdapter;
    private EditText edtName;
    private ImageButton imbBack, imbDelete;
    private TextView tvTitle;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        intitView();
        initRecycleViewIcon();
        initRecycleViewColor();
        back();
        initData();
        save();
        delete();
    }

    private void delete() {
        imbDelete.setOnClickListener(v -> showAlertDialog());
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setPositiveButton("OK", (dialog, which) -> finish());
        builder.setNegativeButton("BỎ QUA", null);
        Dialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button buttonPositive = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.orange));
            buttonPositive.setAllCaps(false);
            Button buttonNegative = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.orange));
            buttonNegative.setAllCaps(false);
            @SuppressLint("DiscouragedApi") int titleId = getResources().getIdentifier("alertTitle", "id", "android");
            TextView titleTextView = ((AlertDialog) dialogInterface).findViewById(titleId);
            TextView messageTextView = ((AlertDialog) dialogInterface).findViewById(android.R.id.message);
            if (titleTextView != null) {
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }

            if (messageTextView != null) {
                messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
        dialog.show();
    }

    private void save() {
        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                edtName.setError("Không được nhập");
                edtName.setText("");
                edtName.requestFocus();
            } else {
                Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initData() {
        Item item = (Item) getIntent().getSerializableExtra("item");
        if (item != null) {
            imbDelete.setVisibility(View.VISIBLE);
            tvTitle.setText(item.getText());
            edtName.setText(item.getText());
            int iconPosition = getListIcon().indexOf(item.getIcon());
            rcvIcon.scrollToPosition(iconPosition);
            iconAdapter.setPositionSelected(iconPosition);
            int colorPosition = getListColor().indexOf(item.getColor());
            rcvColor.scrollToPosition(colorPosition);
            colorAdapter.setPositionSelected(colorPosition);
        }
    }

    private void back() {
        imbBack.setOnClickListener(v -> finish());
    }

    private void initRecycleViewColor() {
        colorAdapter = new ColorAdapter(this);
        rcvColor.setAdapter(colorAdapter);
        colorAdapter.setList(getListColor());
    }

    private void initRecycleViewIcon() {
        iconAdapter = new IconAdapter();
        rcvIcon.setAdapter(iconAdapter);
        iconAdapter.setList(getListIcon());
        iconAdapter.changeColor(getListColor().get(0));
    }

    private void intitView() {
        rcvIcon = findViewById(R.id.rcv_icon);
        imbBack = findViewById(R.id.imb_back);
        imbDelete = findViewById(R.id.imb_delete);
        btnSave = findViewById(R.id.btn_save);
        tvTitle = findViewById(R.id.tv_title);
        rcvColor = findViewById(R.id.rcv_color);
        edtName = findViewById(R.id.edt_name);
        EditTextUtils.ListenUnfocus(edtName);
    }


    private List<Integer> getListColor() {

        List<Integer> list = new ArrayList<>();
        list.add(R.color.black);
        list.add(R.color.orange);
        list.add(R.color.blue);
        list.add(R.color.green);
        list.add(R.color.green1);
        list.add(R.color.green2);
        list.add(R.color.orange2);
        list.add(R.color.pink);
        list.add(R.color.yellow);
        list.add(R.color.yellow1);
        list.add(R.color.blue_sky);
        list.add(R.color.brown);
        list.add(R.color.red);
        list.add(R.color.grey);
        list.add(R.color.orange_1);
        return list;
    }


    private List<Integer> getListIcon() {

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.icon_coffee);
        list.add(R.drawable.icon_coin);
        list.add(R.drawable.icon_education);
        list.add(R.drawable.icon_gift_box);
        list.add(R.drawable.icon_house);
        list.add(R.drawable.icon_incomes);
        list.add(R.drawable.icon_lipstick);
        list.add(R.drawable.icon_liquid);
        list.add(R.drawable.icon_medicine);
        list.add(R.drawable.icon_piggy);
        list.add(R.drawable.icon_shirt);
        list.add(R.drawable.icon_subway);
        list.add(R.drawable.icon_smartphone);
        list.add(R.drawable.icon_tap_faucet);
        list.add(R.drawable.icon_wallet);

        //tránh lặp icon đã thêm, hãy kiểm tra và loại bỏ icon đã tồn tại

        //.......

        return list;
    }

    @Override
    public void ColorSelected(int colorResourceId) {
        iconAdapter.changeColor(colorResourceId);
    }
}