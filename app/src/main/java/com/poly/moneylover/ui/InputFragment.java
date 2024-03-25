package com.poly.moneylover.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.network.CategoryApi;
import com.poly.moneylover.network.TransactionApi;
import com.poly.moneylover.ui.category.EditActivity;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemAdapter;
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.utils.Convert;
import com.poly.moneylover.utils.Device;
import com.poly.moneylover.utils.EditTextUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;

public class InputFragment extends Fragment implements ItemOnclick {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    private Button btnTab1, btnTab2, btnInput;

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private EditText edtMoney, edtNote;
    private TextView tvSelectedDate;
    private ImageButton imbIncreaseDay, imbReduceDay, imbPen;

    private int TYPE = 0;

    private Calendar calendar;

    private Category category;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initRecycleView();
        formatMoney();
        selectDate();
        increaseDay();
        reduceDay();
        save();
    }

    private void getListExpense() {
        @SuppressLint("NotifyDataSetChanged") Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListExpense().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) requireActivity().runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.setPositionSelected(0);
                    });
                }
            } catch (HttpException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show());
            }
        });
        thread.start();
    }

    private void getListRevenue() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListRevenue().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) requireActivity().runOnUiThread(() -> {
                        itemAdapter.setList(data.body());
                        if (!data.body().isEmpty()) itemAdapter.setPositionSelected(0);
                    });
                }
            } catch (HttpException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show());

            }
        });
        thread.start();

    }


    private void save() {
        imbPen.setOnClickListener(v -> saveRecord());
        btnInput.setOnClickListener(v -> saveRecord());
    }

    private void saveRecord() {
        try {
            String money = edtMoney.getText().toString().trim().replaceAll(",", "");
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

    private void createTransaction(Transaction transaction) {
        Thread thread = new Thread(() -> {
            try {
                Response<Void> call = TransactionApi.api.create(transaction).execute();
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
        requireActivity().runOnUiThread(() -> {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            clearData();
        });
    }

    private void clearData() {
        Device.hideKeyBoard(requireActivity());
        edtMoney.setText("0");
        edtNote.setText("");
        edtMoney.clearFocus();
        edtNote.clearFocus();
    }

    private void showAlertDialog(Transaction transaction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Số tiền vẫn là 0, bạn có muốn tiếp tục?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            createTransaction(transaction);
        });
        builder.setNegativeButton("Bỏ qua", null);
        Dialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button buttonPositive = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPositive.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange));
            buttonPositive.setAllCaps(false);
            Button buttonNegative = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNegative.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange));
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

    private String getDate(int value) {
        calendar.add(Calendar.DAY_OF_MONTH, value);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        setCalendar(year, month, day);
        return day + "/" + (month + 1) + "/" + year + " " + Convert.ConvertDayOfWeekString(year, month, day);
    }

    private void setCalendar(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    private void selectDate() {
        tvSelectedDate.setOnClickListener(v -> openDialog());
    }

    private void formatMoney() {
        edtMoney.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (edtMoney.getText().toString().equals("0")) {
                    edtMoney.setText("");
                }
            } else {
                if (edtMoney.getText().toString().trim().isEmpty()) {
                    edtMoney.setText("0");
                }
            }
        });
        edtMoney.addTextChangedListener(new TextWatcher() {

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
                        edtMoney.setText(formattedString);
                        edtMoney.setSelection(formattedString.length());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initRecycleView() {
        itemAdapter = new ItemAdapter(this,getContext());
        recyclerView.setAdapter(itemAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        btnTab1 = view.findViewById(R.id.btn_tab1);
        btnTab2 = view.findViewById(R.id.btn_tab2);
        btnTab1.setSelected(true);
        btnTab1.setOnClickListener(v -> {
            btnTab1.setSelected(true);
            btnTab2.setSelected(false);
            getListExpense();
            itemAdapter.setPositionSelected(0);
            TYPE = 0;
            btnInput.setText("Nhập khoản Tiền chi");
        });
        btnTab2.setOnClickListener(v -> {
            btnTab1.setSelected(false);
            btnTab2.setSelected(true);
            getListRevenue();
            itemAdapter.setPositionSelected(0);
            TYPE = 1;
            btnInput.setText("Nhập khoản Tiền thu");
        });
        imbPen = view.findViewById(R.id.imb_pen);
        btnInput = view.findViewById(R.id.btn_input);
        recyclerView = view.findViewById(R.id.rcv_item);
        edtMoney = view.findViewById(R.id.edt_money);
        edtNote = view.findViewById(R.id.edt_note);
        imbIncreaseDay = view.findViewById(R.id.imb_increase_day);
        imbReduceDay = view.findViewById(R.id.imb_reduce_day);
        tvSelectedDate = view.findViewById(R.id.tv_selected_date);
        EditTextUtils.ListenUnfocus(edtMoney);
        EditTextUtils.ListenUnfocus(edtNote);
        calendar = Calendar.getInstance();
        tvSelectedDate.setText(getDate(0));
    }


    private void openDialog() {

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(), R.style.DialogTheme, (view, year1, month1, day1) -> {
            String date = day1 + "/" + (month1 + 1) + "/" + year1 + " " + Convert.ConvertDayOfWeekString(year1, month1, day1);
            tvSelectedDate.setText(date);
            setCalendar(year1, month1, day1);
        }, year, month, day);
        dialog.show();
    }


    @Override
    public void onSelectedCategory(Category category) {
        this.category = category;
    }

    @Override
    public void editItem() {
        Intent intent = new Intent(requireContext(), EditActivity.class);
        intent.putExtra("type", TYPE);
        requireActivity().startActivity(intent);
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