package com.poly.moneylover.ui.OtherCategories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemAdapterHorizontal;
import com.poly.moneylover.adapters.RecycleViewItemTouchHelper;
import com.poly.moneylover.interfaces.DeleteCategory;
import com.poly.moneylover.interfaces.ItemHorizontalTouchHelper;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.network.CategoryApi;
import com.poly.moneylover.ui.category.NewItemActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;


public class ListActivity extends AppCompatActivity implements ItemHorizontalTouchHelper, DeleteCategory {
    private ImageView imbBack;
    private TextView btnTab1, btnTab2,out;
    RelativeLayout imbAdd;
    private RecyclerView recyclerView;
    private ItemAdapterHorizontal adapter;

    private int tabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        tabIndex = getIntent().getIntExtra("type", 0);
        initView();
        back();
        selectedTab();
        initRecycleview();
        addNew();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTab();
    }

    private void setTab() {
        if (tabIndex == 0) {
            getListExpense();
            btnTab1.setSelected(true);
            btnTab1.setTextColor(Color.parseColor("#FFA500"));
            btnTab2.setSelected(false);
            btnTab2.setTextColor(Color.parseColor("#000000"));
        } else {
            btnTab1.setSelected(false);
            btnTab1.setTextColor(Color.parseColor("#000000"));
            getListRevenue();
            btnTab2.setSelected(true);
            btnTab2.setTextColor(Color.parseColor("#FFA500"));
        }
    }

    private void getListExpense() {
        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListExpense().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null)
                        runOnUiThread(() -> {
                            adapter.setList(data.body());
                        });
                }
            } catch (HttpException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
                );
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
                );
            }
        });
        thread.start();
    }

    private void getListRevenue() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListRevenue().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null)
                        runOnUiThread(() ->
                                adapter.setList(data.body())
                        );
                }
            } catch (HttpException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
                );
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
                );

            }
        });
        thread.start();

    }

    private void addNew() {
        imbAdd.setOnClickListener(v -> gotoActivity());
    }

    private void gotoActivity() {
        Intent intent = new Intent(this, NewItemActivity.class);
        intent.putExtra("tabIndex", tabIndex);
        startActivity(intent);
    }

    private void initRecycleview() {
        adapter = new ItemAdapterHorizontal(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new RecycleViewItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    private void selectedTab() {
        btnTab1.setOnClickListener(v -> {
            btnTab1.setSelected(true);
            btnTab1.setTextColor(Color.parseColor("#FFA500"));
            btnTab2.setSelected(false);
            btnTab2.setTextColor(Color.parseColor("#000000"));
            getListExpense();
            tabIndex = 0;
        });
        btnTab2.setOnClickListener(v -> {
            btnTab1.setSelected(false);
            btnTab1.setTextColor(Color.parseColor("#000000"));
            btnTab2.setSelected(true);
            btnTab2.setTextColor(Color.parseColor("#FFA500"));
            getListRevenue();
            tabIndex = 1;
        });
    }


    private void back() {
        imbBack.setOnClickListener(v -> finish());
        out.setOnClickListener(v -> finish());
    }

    private void initView() {
        imbBack = findViewById(R.id.imb_back);
        imbAdd = findViewById(R.id.imb_add);
        out = findViewById(R.id.out);
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


    private void undoCategory(String msg, int position, Category category) {
        runOnUiThread(() -> {
            adapter.insertCategory(position, category);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void delete(int position, Category category) {
        Thread thread = new Thread(() -> {
            try {
                Response<Boolean> call = CategoryApi.api.delete(category.getId()).execute();
                if (!call.isSuccessful() || Boolean.FALSE.equals(call.body())) {
                    undoCategory("Xóa thất bại", position, category);
                }
            } catch (HttpException e) {
                e.printStackTrace();
                undoCategory("Đã xảy ra lỗi khi xóa", position, category);
            } catch (IOException e) {
                e.printStackTrace();
                undoCategory("Không có kết nối mạng", position, category);
            }
        });
        thread.start();
    }
}