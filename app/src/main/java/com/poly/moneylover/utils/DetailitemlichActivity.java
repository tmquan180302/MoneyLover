package com.poly.moneylover.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemAdapter;
import com.poly.moneylover.adapters.ItemAdapterHorizontal;
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Dto_item;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.CategoryApi;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.ui.category.EditActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;

public class DetailitemlichActivity extends AppCompatActivity implements ItemOnclick {
    private ImageButton back;
    private ImageButton edit;
    private int TYPE = 0;
    private ImageButton imbReduceDay;
    private ItemAdapterHorizontal adapter123;
    private TextView tvSelectedDate,xoa;
    private ImageButton imbIncreaseDay;
    private EditText edtNote;
    private EditText edtMoney;
    private RecyclerView rcvItem;
    private Button btnInput;
    private int INDEX = 0;
    private ItemAdapter itemAdapter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitemlich);
        anhxa();
        getdata();
        initRecycleView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });
         xoa.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(DetailitemlichActivity.this);
                 builder.setTitle("Xác nhận xóa");
                 builder.setMessage("Bạn có chắc chắn muốn xóa dữ liệu không?");


                 builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 });

// Thêm nút hủy bỏ
                 builder.setNegativeButton("Hủy", null);

// Hiển thị hộp thoại
                 builder.show();

             }
         });
    }

    private void anhxa() {
        xoa = findViewById(R.id.xoa);
        back = (ImageButton) findViewById(R.id.back);
        edit = (ImageButton) findViewById(R.id.edit);
        imbReduceDay = (ImageButton) findViewById(R.id.imb_reduce_day);
        tvSelectedDate = (TextView) findViewById(R.id.tv_selected_date);
        imbIncreaseDay = (ImageButton) findViewById(R.id.imb_increase_day);
        edtNote = (EditText) findViewById(R.id.edt_note);
        edtMoney = (EditText) findViewById(R.id.edt_money);
        rcvItem = (RecyclerView) findViewById(R.id.rcv_item);
        btnInput = (Button) findViewById(R.id.btn_input);
    }
    private void initRecycleView() {
        itemAdapter = new ItemAdapter(this);
        rcvItem.setAdapter(itemAdapter);
    }
    private void getdata() {
        // Nhận Intent đã gửi
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                 id = bundle.getString("id");
                String category = bundle.getString("category");
                String day = bundle.getString("day");
                String note = bundle.getString("note");
                String price = bundle.getString("price");
                intent.putExtras(bundle);
                // Tạo một đối tượng YourDataModel (giả sử đã có dữ liệu)
                Transaction yourData = new Transaction();
                yourData.setDay(Long.valueOf(day)); // Gán giá trị millis cho trường "day"
                String dateString = yourData.convertDayToDateString();

                edtNote.setText(note);
                tvSelectedDate.setText(dateString);
                edtMoney.setText(price);

        }



    }
    }


    @Override
    public void onSelectedCategory(Category category) {

    }


    @Override
    public void editItem() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("type", TYPE);
       startActivity(intent);
    }
    private void getListExpense() {
        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListExpense().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.setPositionSelected(0);
                    });
                }
            } catch (HttpException e) {
                e.printStackTrace();
              runOnUiThread(() -> Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
              runOnUiThread(() -> Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show());
            }
        });
        thread.start();
    }
    private void getListRevenue() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListRevenue().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null)runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.setPositionSelected(0);
                    });
                }
            } catch (HttpException e) {
                e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
              runOnUiThread(() -> Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show());

            }
        });
        thread.start();

    }



    public void delete1() {
        Thread thread = new Thread(() -> {
            try {
                Response<Boolean> call = TransactionApi.api.delete1(id).execute();
                if (!call.isSuccessful() || Boolean.FALSE.equals(call.body())) {

                    Toast.makeText(this, "xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (HttpException e) {
                e.printStackTrace();
                Toast.makeText(this, "xảy ra lỗi khi xóa", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "không có kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
        thread.start();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (TYPE == 0) {
            getListExpense();
        } else {
            getListRevenue();
        }
    }
}