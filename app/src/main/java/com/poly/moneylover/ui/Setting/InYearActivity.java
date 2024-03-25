package com.poly.moneylover.ui.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.YearListener;
import com.poly.moneylover.ui.InYear.ChiFragment;
import com.poly.moneylover.ui.InYear.ThuFragment;
import com.poly.moneylover.ui.InYear.TongFragment;

import java.util.Calendar;

public class InYearActivity extends AppCompatActivity {
    Fragment selectedFragment = null;
    LinearLayout lnYear;
    TextView tvYear;
    TextView chi;
    TextView thu;
    TextView tong;
    ImageView imgBack, imgPre, imgNext;
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_year);


        chi = findViewById(R.id.chitieu);
        thu = findViewById(R.id.thunhap);
        tong = findViewById(R.id.tong);
        tvYear = findViewById(R.id.tvYear);
        lnYear = findViewById(R.id.lnYear);
        imgBack = findViewById(R.id.imgBack);
        imgPre = findViewById(R.id.imgPre);
        imgNext = findViewById(R.id.imgNext);

        tvYear.setText(String.valueOf(currentYear));
        lnYear.setOnClickListener(v -> showYearPickerDialog());
        selectedFragment = new ChiFragment();
        getFragment();
        chi.setBackgroundResource(R.drawable.backgroundtron);
        thu.setBackgroundResource(R.drawable.backgroundtron1);
        tong.setBackgroundResource(R.drawable.backgroundtron1);
        onImgClick();
    }
    @Override
    protected void onResume() {
        super.onResume();

        chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ChiFragment();
                getFragment();
                chi.setBackgroundResource(R.drawable.backgroundtron);
                thu.setBackgroundResource(R.drawable.backgroundtron1);
                tong.setBackgroundResource(R.drawable.backgroundtron1);
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ThuFragment();
                getFragment();
                chi.setBackgroundResource(R.drawable.backgroundtron1);
                thu.setBackgroundResource(R.drawable.backgroundtron);
                tong.setBackgroundResource(R.drawable.backgroundtron1);
            }
        });
        tong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment =new TongFragment();
                getFragment();
                chi.setBackgroundResource(R.drawable.backgroundtron1);
                thu.setBackgroundResource(R.drawable.backgroundtron1);
                tong.setBackgroundResource(R.drawable.backgroundtron);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentinyear, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
    private void onImgClick(){
        imgBack.setOnClickListener(v -> {
            finish();
        });
        imgPre.setOnClickListener(v ->{
            int year = Integer.parseInt(tvYear.getText().toString()) - 1;
            tvYear.setText(String.valueOf(year));
            getFragment();
        });
        imgNext.setOnClickListener(v ->{
            int year = Integer.parseInt(tvYear.getText().toString()) + 1;
            tvYear.setText(String.valueOf(year));
            getFragment();
        });
    }

    private void showYearPickerDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_year_picker);
        dialog.setTitle("Chọn năm");

        NumberPicker numberPicker = dialog.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1900);
        numberPicker.setMaxValue(2100);
        numberPicker.setValue(currentYear);

        Button buttonOK = dialog.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedYear = numberPicker.getValue();
                tvYear.setText(String.valueOf(selectedYear));
                dialog.dismiss();
                getFragment();
            }
        });

        dialog.show();
    }
    public void getFragment(){
        int year = Integer.parseInt(tvYear.getText().toString());
        if (selectedFragment instanceof YearListener) {
            ((YearListener) selectedFragment).onYearSelected(year);
        }
        loadFragment(selectedFragment);
    }
}
