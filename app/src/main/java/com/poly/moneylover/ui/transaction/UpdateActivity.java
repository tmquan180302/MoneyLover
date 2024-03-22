package com.poly.moneylover.ui.transaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemAdapter;
import com.poly.moneylover.databinding.ActivityUpdateBinding;
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.ExpenseItem2;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.CategoryApi;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.ui.InputFragment;
import com.poly.moneylover.ui.category.EditActivity;
import com.poly.moneylover.utils.Convert;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.HttpException;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity implements ItemOnclick {

    private ActivityUpdateBinding binding;
    private ExpenseItem2 expenseItem;
    private ItemAdapter itemAdapter;
    private Calendar calendar;
    private String idCate;
    private Category category;
    private int TYPE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar = Calendar.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            expenseItem = getIntent().getSerializableExtra("data", ExpenseItem2.class);
        } else {
            expenseItem = (ExpenseItem2) getIntent().getSerializableExtra("data");
        }

        idCate = getIntent().getStringExtra("idCate");
        TYPE = getIntent().getIntExtra("type", 0);

        itemAdapter = new ItemAdapter(this);
        binding.rcvItem.setAdapter(itemAdapter);

        Log.e("TAG", "onCreate: " + expenseItem );

        if (expenseItem.getExpenseItem() == null){
            return;
        }

        binding.edtMoney.setText(Convert.convertNumber(Long.parseLong(expenseItem.getExpenseItem().getPrice())));

        if (!Objects.equals(expenseItem.getExpenseItem().getNote(), "")){
            binding.edtNote.setText(expenseItem.getExpenseItem().getNote());
        }

        if (TYPE == 0) {
            getListExpense();
        } else {
            getListRevenue();
        }

        binding.tvSelectedDate.setText(setDate(Convert.getTimeStamp(expenseItem.getExpenseItem().getDate())));

        binding.imbReduceDay.setOnClickListener(v -> binding.tvSelectedDate.setText(getDate(-1)));
        binding.imbIncreaseDay.setOnClickListener(v -> binding.tvSelectedDate.setText(getDate(1)));

        binding.btnUpdate.setOnClickListener(v -> updateRecord());

        binding.imbPen.setOnClickListener(v -> updateRecord());

        binding.btnCopy.setOnClickListener(v -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view,
                        InputFragment.newInstance(binding.edtNote.getText().toString(), binding.edtMoney.getText().toString(), category.getId(), TYPE))
                .commit());

        binding.imvBack.setOnClickListener(v -> finish());

        binding.tvSelectedDate.setOnClickListener(v -> openDialog());

        binding.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bạn Chắc Chắn Muốn Xóa ? ");
            builder.setPositiveButton("OK", (dialog, which) -> {
                deleteTransaction();
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

    private void updateRecord() {
        try {
            String money = binding.edtMoney.getText().toString().trim().replaceAll(",", "");
            if (money.equals("")) money = "0";
            Long price = Long.parseLong(money);
            Long time = calendar.getTimeInMillis();
            String note = binding.edtNote.getText().toString().trim();
            Transaction transaction = new Transaction(category, time, note, price);
            Log.e("saveRecord: ", transaction.toString());
            if (money.equals("0")) {
                showAlertDialog(transaction);
            } else {
                updateTransaction(transaction);
            }
        } catch (NumberFormatException e) {
            String money = binding.edtMoney.getText().toString().trim().replaceAll("\\.", "");
            if (money.equals("")) money = "0";
            Long price = Long.parseLong(money);
            Long time = calendar.getTimeInMillis();
            String note = binding.edtNote.getText().toString().trim();
            Transaction transaction = new Transaction(category, time, note, price);
            Log.e("saveRecord: ", transaction.toString());
            if (money.equals("0")) {
                showAlertDialog(transaction);
            } else {
                updateTransaction(transaction);
            }
        }
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

    private void getListExpense() {
        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListExpense().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.changePositionSelected(idCate);
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
                    if (data.body() != null) runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.changePositionSelected(idCate);
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

    private void openDialog() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year1, month1, day1) -> {
            String date = day1 + "/" + (month1 + 1) + "/" + year1 + " " + Convert.ConvertDayOfWeekString(year1, month1, day1);
            binding.tvSelectedDate.setText(date);
            setCalendar(year1, month1, day1);
        }, year, month, day);
        dialog.show();
    }

    private void updateTransaction(Transaction transaction) {
        Thread thread = new Thread(() -> {
            try {
                Response<Void> call = TransactionApi.api.update(transaction, expenseItem.getExpenseItem().getId()).execute();
                Log.e("call", call.toString());
                if (call.isSuccessful() && call.code() == 200) {
                    showMessage("Sửa thành công");
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    showMessage("Sửa thất bại");
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


    private void deleteTransaction() {
        Thread thread = new Thread(() -> {
            try {
                Response<Void> call = TransactionApi.api.delete(expenseItem.getExpenseItem().getId()).execute();
                Log.e("call", call.toString());
                if (call.isSuccessful() && call.code() == 200) {
                    showMessage("Xóa thành công");
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    showMessage("Xóa thất bại");
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

    private String setDate(Long timeStamp) {
        calendar.setTimeInMillis(timeStamp);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return day + "/" + (month + 1) + "/" + year + " " + Convert.ConvertDayOfWeekString(year, month, day);
    }

    private void setCalendar(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    @Override
    public void onSelectedCategory(Category category) {
        this.category = category;
    }

    @Override
    public void editItem() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("type", TYPE);
        startActivity(intent);
        finish();
    }
}