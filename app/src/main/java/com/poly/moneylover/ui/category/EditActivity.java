package com.poly.moneylover.ui.category;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemAdapterHorizontal;
import com.poly.moneylover.adapters.RecycleViewItemTouchHelper;
import com.poly.moneylover.interfaces.ItemHorizontalTouchHelper;
import com.poly.moneylover.models.Item;

import java.util.List;

public class EditActivity extends AppCompatActivity implements ItemHorizontalTouchHelper {
    private ImageButton imbBack, imbAdd;
    private Button btnTab1, btnTab2;
    private RecyclerView recyclerView;
    private ItemAdapterHorizontal adapter;

    private List<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        back();
        selectedTab();
        initRecycleview();
        addNew();
        setTab();
    }

    private void setTab() {
        if(getIntent().getIntExtra("type",0) == 0){
            list = Item.getListItemTienChi();
            btnTab1.setSelected(true);
            btnTab2.setSelected(false);
        }else {
            btnTab1.setSelected(false);
            list = Item.getListItemTienThu();
            btnTab2.setSelected(true);
        }
        adapter.setList(list);
    }

    private void addNew() {
        imbAdd.setOnClickListener(v -> startActivity(new Intent(this, NewItemActivity.class)));
    }

    private void initRecycleview() {
        adapter = new ItemAdapterHorizontal();
        DividerItemDecoration decoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        list = Item.getListItemTienChi();
        adapter.setList(list);
        ItemTouchHelper.SimpleCallback simpleCallback = new RecycleViewItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    private void selectedTab() {
        btnTab1.setOnClickListener(v -> {
            btnTab1.setSelected(true);
            btnTab2.setSelected(false);
            list = Item.getListItemTienChi();
            adapter.setList(list);
        });
        btnTab2.setOnClickListener(v -> {
            btnTab1.setSelected(false);
            btnTab2.setSelected(true);
            list = Item.getListItemTienThu();
            adapter.setList(list);
        });
    }

    private void back() {
        imbBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        imbBack = findViewById(R.id.imb_back);
        imbAdd = findViewById(R.id.imb_add);
        btnTab1 = findViewById(R.id.btn_tab1);
        btnTab2 = findViewById(R.id.btn_tab2);
        recyclerView = findViewById(R.id.rcv_item_horizontal);
        

    }

    @Override
    public void onSwide(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ItemAdapterHorizontal.ItemViewHolder) {
            int position = viewHolder.getAdapterPosition();
            adapter.refreshItem(position);
            showAlertDialog(position);
        }
    }

    private void showAlertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setPositiveButton("OK", (dialog, which) -> adapter.removeItem(position));
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


}