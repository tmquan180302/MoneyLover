package com.poly.moneylover.ui.transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemAdapter;
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.CategoryApi;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.ui.InputFragment;
import com.poly.moneylover.ui.category.EditActivity;
import com.poly.moneylover.utils.CalendarUtils;
import com.poly.moneylover.utils.Convert;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class UpdateTransactionActivity extends AppCompatActivity implements ItemOnclick {

    private ImageButton imbBack, imbUpdate, imbPreDay, imbNextDay;
    private TextView tvDay, tvCopy, tvDelete;
    private EditText edtNote, edtPrice;
    private RecyclerView rcvCategory;
    private Button btnUpdate;
    private ItemAdapter itemAdapter;
    private Calendar calendar;
    private Category category;

    private long day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_transaction);
        initView();
        back();
        edtPriceAction();
        updateAction();
        pickDateAction();
        initRcvCategory();
        copyAction();
        deleteAction();
    }

    private void edtPriceAction() {
        edtPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (edtPrice.getText().toString().equals("0")) {
                    edtPrice.setText("");
                }
            } else {
                if (edtPrice.getText().toString().trim().isEmpty()) {
                    edtPrice.setText("0");
                }
            }
        });
        edtPrice.addTextChangedListener(new TextWatcher() {

            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }
                String originalString = s.toString();
                String cleanString = originalString.replaceAll(",", "");
                try {
                    long result = Long.parseLong(cleanString);
                    String formattedString = Convert.FormatNumber(result);
                    if (!formattedString.equals(originalString)) {
                        isUpdating = true;
                        edtPrice.setText(formattedString);
                        edtPrice.setSelection(formattedString.length());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void deleteAction() {
        String id = getIntent().getStringExtra("id");
        tvDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bạn Chắc Chắn Muốn Xóa ? ");
            builder.setPositiveButton("OK", (dialog, which) -> {
                deleteTransaction(id);
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
        });
    }

    private void deleteTransaction(String id) {
        Thread thread = new Thread(() -> {
            try {

                TransactionApi.api.delete(id).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            showMessage("Xóa thành công");
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            showMessage("Xóa thất bại");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void copyAction() {
        tvCopy.setOnClickListener(v -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view,
                        InputFragment.newInstance(edtNote.getText().toString(),
                                edtPrice.getText().toString(), category.getId(), category.getType(),
                                calendar.getTimeInMillis()))
                .commit());
    }


    private void initRcvCategory() {

        int typeCategory = getIntent().getIntExtra("categoryType", 0);
        String idCategory = getIntent().getStringExtra("categoryId");

        itemAdapter = new ItemAdapter(this, getApplicationContext());
        rcvCategory.setAdapter(itemAdapter);

        if (typeCategory == 0) {
            getListExpense(idCategory);
        } else {
            getListRevenue(idCategory);
        }
    }

    private void getListRevenue(String idCategory) {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListRevenue().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.changePositionSelected(idCategory);
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

    private void getListExpense(String idCategory) {

        Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListExpense().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.changePositionSelected(idCategory);
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

    private void updateAction() {


        imbUpdate.setOnClickListener(v -> {

            try {
                String money = edtPrice.getText().toString().trim().replaceAll(",", "");
                if (money.equals("")) money = "0";
                Long price = Long.parseLong(money);
                Long time = calendar.getTimeInMillis();
                String note = edtNote.getText().toString().trim();
                Transaction transaction = new Transaction(category, time, note, price);
                Log.e("saveRecord: ", transaction.toString());
                if (money.equals("0")) {
                    showAlertDialog(transaction);
                } else {
                    updateTransaction(transaction);
                }
            } catch (NumberFormatException e) {
                String money = edtPrice.getText().toString().trim().replaceAll("\\.", "");
                if (money.equals("")) money = "0";
                Long price = Long.parseLong(money);
                Long time = calendar.getTimeInMillis();
                String note = edtNote.getText().toString().trim();
                Transaction transaction = new Transaction(category, time, note, price);
                Log.e("saveRecord: ", transaction.toString());
                if (money.equals("0")) {
                    showAlertDialog(transaction);
                } else {
                    updateTransaction(transaction);
                }
            }
        });

        btnUpdate.setOnClickListener(v -> {

            try {
                String money = edtPrice.getText().toString().trim().replaceAll(",", "");
                if (money.equals("")) money = "0";
                Long price = Long.parseLong(money);
                Long time = calendar.getTimeInMillis();
                String note = edtNote.getText().toString().trim();
                Transaction transaction = new Transaction(category, time, note, price);
                Log.e("saveRecord: ", transaction.toString());
                if (money.equals("0")) {
                    showAlertDialog(transaction);
                } else {
                    updateTransaction(transaction);
                }
            } catch (NumberFormatException e) {
                String money = edtPrice.getText().toString().trim().replaceAll("\\.", "");
                if (money.equals("")) money = "0";
                Long price = Long.parseLong(money);
                Long time = calendar.getTimeInMillis();
                String note = edtNote.getText().toString().trim();
                Transaction transaction = new Transaction(category, time, note, price);
                Log.e("saveRecord: ", transaction.toString());
                if (money.equals("0")) {
                    showAlertDialog(transaction);
                } else {
                    updateTransaction(transaction);
                }
            }


        });
    }

    private void showAlertDialog(Transaction transaction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Số tiền vẫn là 0, bạn có muốn tiếp tục?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            updateTransaction(transaction);
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

    private void updateTransaction(Transaction transaction) {
        String id = getIntent().getStringExtra("id");

        Thread thread = new Thread(() -> {
            try {
                TransactionApi.api.update(transaction, id).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful() && response.code() == 200) {
                            showMessage("Cập nhật thành công");
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            showMessage("Cập nhật thất bại");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void pickDateAction() {
        imbPreDay.setOnClickListener(v -> {
            tvDay.setText(getDate(-1));
        });
        imbNextDay.setOnClickListener(v -> {
            tvDay.setText(getDate(1));
        });
        tvDay.setOnClickListener(v -> openDialog());
    }

    private void openDialog() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year1, month1, day1) -> {
            String date = day1 + "/" + (month1 + 1) + "/" + year1 + " " + Convert.ConvertDayOfWeekString(year1, month1, day1);
            tvDay.setText(date);
            setCalendar(year1, month1, day1 + 1);
        }, year, month, day);
        dialog.show();
    }

    private void setCalendar(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day - 1);
    }

    private String getDate(int value) {
        calendar.add(Calendar.DAY_OF_MONTH, value);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        setCalendar(year, month, day + 1);
        return day + "/" + (month + 1) + "/" + year + " " + Convert.ConvertDayOfWeekString(year, month, day);
    }

    private String setDate(Long timeStamp) {
        calendar.setTimeInMillis(timeStamp);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return day + "/" + (month + 1) + "/" + year + " " + Convert.ConvertDayOfWeekString(year, month, day);
    }

    private void back() {
        imbBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        imbBack = findViewById(R.id.imb_back);
        imbUpdate = findViewById(R.id.imb_update);
        imbPreDay = findViewById(R.id.imb_preDay);
        imbNextDay = findViewById(R.id.imb_nextDay);
        tvDay = findViewById(R.id.tv_day);
        tvCopy = findViewById(R.id.tv_copy);
        tvDelete = findViewById(R.id.tv_delete);
        edtNote = findViewById(R.id.edt_note);
        edtPrice = findViewById(R.id.edt_price);
        rcvCategory = findViewById(R.id.rcv_category);
        btnUpdate = findViewById(R.id.btn_update);

        day = getIntent().getLongExtra("day", 0);
        tvDay.setText(Convert.getDayConvert(day));
        edtNote.setText(getIntent().getStringExtra("note"));
        edtPrice.setText(Convert.FormatNumber(getIntent().getLongExtra("price", 0)));

        calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(day);

    }

    @Override
    public void onSelectedCategory(Category category) {
        this.category = category;
    }

    @Override
    public void editItem() {
        int typeCategory = getIntent().getIntExtra("categoryType", 0);
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("type", typeCategory);
        startActivity(intent);
        finish();
    }
}