package com.poly.moneylover.ui.ThuChiCoDinh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.CategorySpinnerAdapter;
import com.poly.moneylover.adapters.FrequencySpinnerAdapter;
import com.poly.moneylover.databinding.ActivityThemSuaBinding;
import com.poly.moneylover.models.Budget;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Request.BudgetCreateRequest;
import com.poly.moneylover.network.BudgetApi;
import com.poly.moneylover.network.CategoryApi;
import com.poly.moneylover.ui.ReportFragment;
import com.poly.moneylover.ui.service.ServiceActivity;
import com.poly.moneylover.ui.transaction.fragment.IncomeFragment;
import com.poly.moneylover.ui.transaction.fragment.OutcomeFragment;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.HttpException;
import retrofit2.Response;


public class ThemSuaActivity extends AppCompatActivity {
    private ActivityThemSuaBinding binding;
    private CategorySpinnerAdapter adapter;
    private FrequencySpinnerAdapter frequencySpinnerAdapter;
    private List<Category> list = new ArrayList<>();
    private Budget budget = new Budget();
    private boolean isTypeChi = true;
    private boolean isFromReport = false;
    private Calendar startDayCalendar = Calendar.getInstance();
    private Calendar endDayCalendar = Calendar.getInstance();
    public static final String DATA_BUDGET = "DATA_BUDGET";
    private List<String> listFrequency = Arrays.asList(
            "Không bao giờ",
            "Hàng ngày",
            "Hàng tuần",
            "Hàng hai tuần",
            "Hàng ba tuần",
            "Hàng bốn tuần",
            "Hàng tháng",
            "Hàng hai tháng",
            "Hàng ba tháng",
            "Hàng bốn tháng",
            "Hàng nửa năm",
            "Hàng năm"
    );
    private List<String> listServerValues = Arrays.asList(
            "Never",
            "Daily",
            "Weekly",
            "Biweekly",
            "Triweekly",
            "Quadweekly",
            "Monthly",
            "Bimonthly",
            "Trimonthly",
            "Quadmonthly",
            "Semiannually",
            "Annually"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSuaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClickAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        String mesage = "Nếu Ngày kết thúc là 'Không', xin lưu ý rằng chi phí cố định sẽ không được hiển thị trên lịch ngay lập tức, trừ khi đó là ngày tương ứng. Ngược lại, nếu bạn đặt Ngày kết thúc, nó sẽ được hiển thị luôn trên lịch và báo cáo màn hình.";
        binding.tvMessage.setText(mesage);
        budget = (Budget) getIntent().getSerializableExtra(DATA_BUDGET);
//        isFromReport = getIntent().getBooleanExtra("location", false);

        frequencySpinnerAdapter = new FrequencySpinnerAdapter(this, listFrequency, listServerValues);
        binding.spnFrequency.setAdapter(frequencySpinnerAdapter);
        binding.spnFrequency.setSelection(6);
        frequencySpinnerAdapter.notifyDataSetChanged();
        if (budget == null) {
            getListExpense();
        }

        if (list != null) {
            adapter = new CategorySpinnerAdapter(this, list);
            binding.spnCategory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);

        if (budget == null) {
            binding.tvStartDay.setText(formattedDate);
        } else {
            validateFrequency();
            int type = budget.getCategory().getType();
            if (type == 0) {
                isTypeChi = true;
                getListExpense();
                binding.btnChi.setSelected(true);
                binding.btnThu.setSelected(false);
            } else {
                isTypeChi = false;
                getListRevenue();
                binding.btnChi.setSelected(false);
                binding.btnThu.setSelected(true);
            }
            boolean endDayNotNull = (budget.getDayEnd() != null);
            binding.tvStartDay.setText(convertToDateString(budget.getDayStart()));
            binding.tvEndDay.setText((endDayNotNull) ? convertToDateString(Long.parseLong(budget.getDayEnd())) : "Không");
            binding.edtNote.setText(budget.getNote());
            binding.edtPrice.setText(String.valueOf(budget.getPrice()));

        }
    }

    public String convertToDateString(long milliseconds) {
        SimpleDateFormat convertFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(milliseconds);
        String dateString = convertFormat.format(date);

        try {
            Date convertedDate = convertFormat.parse(dateString);
            dateString = outputFormat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public void setDataCreate() {
        String note = binding.edtNote.getText().toString();
        String priceStr = binding.edtPrice.getText().toString();
        Category category = (Category) binding.spnCategory.getSelectedItem();
        String frequency = frequencySpinnerAdapter.getServerValue(binding.spnFrequency.getSelectedItemPosition());
        String startDayStr = binding.tvStartDay.getText().toString();
        String endDayStr = binding.tvEndDay.getText().toString();

        long startDay = convertToMilisecond(startDayStr);
        long endDay = 0;
        int price = Integer.valueOf(priceStr);

        if (!endDayStr.equals("Không")) {
            endDay = convertToMilisecond(endDayStr);
        }

        if (TextUtils.isEmpty(note)) {
            binding.edtNote.setError("Nhập tiêu đề");
            binding.edtNote.setText("");
            binding.edtNote.requestFocus();
        } else if (TextUtils.isEmpty(priceStr)) {
            binding.edtPrice.setError("Nhập số tiền");
            binding.edtPrice.setText("");
            binding.edtPrice.requestFocus();
        } else {
            createOrUpdate(category, startDay, endDay, note, price, frequency);
        }

    }

    public long convertToMilisecond(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

        long timeMilisecound = 0;
        Date startDate;
        try {
            startDate = dateFormat.parse(date);
            String timeString = outputFormat.format(startDate);
            timeMilisecound = outputFormat.parse(timeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeMilisecound;
    }

    public void onClickAction() {
        binding.imgBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnChi.setSelected(true);
        binding.btnChi.setOnClickListener(v -> {
            isTypeChi = true;
            binding.btnChi.setSelected(true);
            binding.btnThu.setSelected(false);
            getListExpense();
            binding.spnCategory.setSelection(0);
        });
        binding.btnThu.setOnClickListener(v -> {
            isTypeChi = false;
            binding.btnThu.setSelected(true);
            binding.btnChi.setSelected(false);
            getListRevenue();
            binding.spnCategory.setSelection(0);
        });
        binding.lnStartDay.setOnClickListener(v -> {
            openDialog(true);
        });
        binding.lnEndDay.setOnClickListener(v -> {
            showOptionEndDay();
        });
        binding.imgPen.setOnClickListener(v -> {
            setDataCreate();
        });

    }

    private void createOrUpdate(Category category, long dayStart, long dayEnd, String note, int price, String frequency) {
        Thread thread = new Thread(() -> {
            try {
                BudgetCreateRequest request = new BudgetCreateRequest(category, dayStart, dayEnd, note, price, frequency);
                if (budget != null) {
                    Response<Void> call = BudgetApi.api.update(budget.get_id(), request).execute();
                    if (call.isSuccessful() && call.code() == 200) {
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        showMessage("Cập nhật thành công", true);
                    } else {
                        showMessage("Cập nhật thất bại", false);

                    }
                } else {
                    Response<Void> call = BudgetApi.api.create(request).execute();
                    if (call.isSuccessful() && call.code() == 201) {
                        showMessage("Thanh cong", true);
                    } else {
                        showMessage("Thêm thất bại", false);
                    }
                }

            } catch (HttpException e) {
                e.printStackTrace();
                showMessage("Đã xảy ra lỗi", false);
            } catch (IOException e) {
                e.printStackTrace();
                showMessage("Mất kết nối với server", false);
            }
        });
        thread.start();
    }

    private void showOptionEndDay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.menu_end_day, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView tvKhong = dialogView.findViewById(R.id.tvKhong);
        TextView tvDate = dialogView.findViewById(R.id.tvDate);

        tvKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvEndDay.setText("Không");
                dialog.dismiss();
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(false);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void openDialog(boolean isStartDay) {
        Calendar selectedCalendar;
        TextView selectedTextView;

        if (isStartDay) {
            selectedCalendar = startDayCalendar;
            selectedTextView = binding.tvStartDay;
        } else {
            selectedCalendar = endDayCalendar;
            selectedTextView = binding.tvEndDay;
        }

        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        int month = selectedCalendar.get(Calendar.MONTH);
        int year = selectedCalendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year1, month1, day1) -> {
            String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", day1, month1 + 1, year1);
            selectedTextView.setText(date);
            setCalendar(selectedCalendar, year1, month1, day1);
        }, year, month, day);
        dialog.show();
    }

    private void setCalendar(Calendar calendar, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    private void validateNewOrEdit(List<Category> list) {
        if (budget != null) {
            for (int i = 0; i < list.size(); i++) {
                Category category = list.get(i);
                if (category.getId().equals(budget.getCategory().getId())) {
                    binding.spnCategory.setSelection(i);
                    break;
                }
            }
        }
    }

    private void validateFrequency() {
        if (budget != null) {
            String serverFrequency = budget.getFrequency();
            int index = listServerValues.indexOf(serverFrequency);
            if (index != -1) {
                binding.spnFrequency.setSelection(index);
            }
        }
    }

    private void getListExpense() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListExpense().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) {
                        runOnUiThread(() -> {
                            list.clear();
                            list.addAll(data.body());
                            validateNewOrEdit(list);
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            } catch (HttpException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Mất kết nối với server", Toast.LENGTH_SHORT).show());
            }
        });
        thread.start();
    }

    private void getListRevenue() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<Category>> data = CategoryApi.api.getListRevenue().execute();
                if (data.isSuccessful()) {
                    if (data.body() != null) {
                        runOnUiThread(() -> {
                            list.clear();
                            list.addAll(data.body());
                            validateNewOrEdit(list);
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            } catch (HttpException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Mất kết nối với server", Toast.LENGTH_SHORT).show());
            }
        });
        thread.start();
    }

    private void showMessage(String msg, Boolean isFinish) {
        runOnUiThread(() -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            if (isFinish) finish();
        });
    }

}