package com.poly.moneylover.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
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

    private Calendar calendar;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitemlich);
        anhxa();
        getdata();
        increaseDay();
        reduceDay();
        initRecycleView();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });selectDate();
         xoa.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(DetailitemlichActivity.this);
                 builder.setTitle("Xác nhận xóa");
                 builder.setMessage("Bạn có chắc chắn muốn xóa dữ liệu không?");


                 builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         String categoryIdToDelete = id; // Thay thế 123 bằng ID thực của category cần xóa
                         int position = 0; // Ví dụ: vị trí của category trong danh sách
                         Transaction transaction = new Transaction(); // Tạo một đối tượng Transaction

                         // Gọi hàm delete2 để xóa category
                        delete();
                         Toast.makeText(DetailitemlichActivity.this, "xóa thành công", Toast.LENGTH_SHORT).show();
finish();

                     }
                 });

// Thêm nút hủy bỏ
                 builder.setNegativeButton("Hủy", null);

// Hiển thị hộp thoại
                 builder.show();

             }
         });
        calendar = Calendar.getInstance();
        tvSelectedDate.setText(getDate(0));
         btnInput.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 saveRecord();
             }
         });
    }
    private String getDate(int value) {
        calendar.add(Calendar.DAY_OF_MONTH, value);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        setCalendar(year, month, day);
        return day + "/" + (month + 1) + "/" + year + " " + Convert.ConvertDayOfWeekString(year, month, day);
    }

    private void saveRecord() {
        try {

            String money = edtMoney.getText().toString().trim().replaceAll(",", "");
            String day = tvSelectedDate.getText().toString();
            if (money.equals("")) money = "0";
            Long price = Long.parseLong(money);
            Long time = calendar.getTimeInMillis();
            String note = edtNote.getText().toString().trim();
            Transaction transaction = new Transaction(category, time, note, price);
            Log.e("saveRecord: ", transaction.toString());
            if (money.equals("0")) {
                showAlertDialog(transaction);
            } else {
                createTransaction(transaction);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    private void selectDate() {
        tvSelectedDate.setOnClickListener(v -> openDialog());
    }
    private void setCalendar(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }
    private void openDialog() {

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year1, month1, day1) -> {
            String date = day1 + "/" + (month1 + 1) + "/" + year1 + " " + Convert.ConvertDayOfWeekString(year1, month1, day1);
            tvSelectedDate.setText(date);
            setCalendar(year1, month1, day1);
        }, year, month, day);
        dialog.show();
    }
    private void createTransaction(Transaction transaction) {
        Thread thread = new Thread(() -> {
            try {
                Response<String> call = TransactionApi.api.update(id,transaction).execute();
                Log.e("call",call.toString());
                if (call.isSuccessful() && call.code() == 200) {
                    showMessage("Thêm thành công");
                } else {
                    showMessage("Thêm thất bại");
                }
            } catch (HttpException e) {
                e.printStackTrace();
                showMessage("Đã xảy ra lỗi");
            } catch (IOException e) {
                e.printStackTrace();
                showMessage("Không có kết nối mạng");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                showMessage("api toang rồi");
            }
        });
        thread.start();
    }
    private void showMessage(String msg) {
    runOnUiThread(() -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            clearData();
        });
    }
    private void clearData() {
        Device.hideKeyBoard(this);
        edtMoney.setText("0");
        edtNote.setText("");
        edtMoney.clearFocus();
        edtNote.clearFocus();
    }
    private void showAlertDialog(Transaction transaction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Số tiền vẫn là 0, bạn có muốn tiếp tục?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            createTransaction(transaction);
        });
        builder.setNegativeButton("Bỏ qua", null);
        Dialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button buttonPositive = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPositive.setTextColor(ContextCompat.getColor(this, R.color.orange));
            buttonPositive.setAllCaps(false);
            Button buttonNegative = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.orange));
            buttonNegative.setAllCaps(false);
            TextView messageTextView = ((AlertDialog) dialogInterface).findViewById(android.R.id.message);
            if (messageTextView != null) {
                messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
        dialog.show();
    }
    private void reduceDay() {
        imbReduceDay.setOnClickListener(v -> tvSelectedDate.setText(getDate(-1)));
    }

    private void increaseDay() {
        imbIncreaseDay.setOnClickListener(v -> tvSelectedDate.setText(getDate(1)));
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
//        getListExpense();
//        itemAdapter.setPositionSelected(0);
//        TYPE = 0;
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

    private void initRecycleView() {
        itemAdapter = new ItemAdapter(this);
        rcvItem.setAdapter(itemAdapter);
    }

    public void delete() {
        Thread thread = new Thread(() -> {
            try {
                Response<Boolean> call = TransactionApi.api.delete(id).execute();
                if (!call.isSuccessful() || Boolean.FALSE.equals(call.body())) {


                }
            } catch (HttpException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }
        });
        thread.start();
    }


    @Override
    public void onSelectedCategory(Category category) {
        this.category = category;
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