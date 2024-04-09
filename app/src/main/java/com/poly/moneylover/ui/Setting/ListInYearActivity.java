package com.poly.moneylover.ui.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.YearListener;
import com.poly.moneylover.ui.ListInYear.ChiFragment;
import com.poly.moneylover.ui.ListInYear.ThuFragment;

import java.util.Calendar;


public class ListInYearActivity extends AppCompatActivity {
    private View leftBar, rightBar;
    Fragment selectedFragment = null;
    private TextView chi, thu;
    private ImageView imgBack, imgPre, imgNext;
    LinearLayout lnYear;
    TextView tvYear;
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_in_year);
        leftBar = findViewById(R.id.leftBar);
        rightBar = findViewById(R.id.rightBar);
        chi = findViewById(R.id.chitieu);
        thu = findViewById(R.id.thunhap);

        tvYear = findViewById(R.id.tvYear);
        lnYear = findViewById(R.id.lnYear);
        imgBack = findViewById(R.id.imgBack);
        imgPre = findViewById(R.id.imgPre);
        imgNext = findViewById(R.id.imgNext);

        tvYear.setText(String.valueOf(currentYear));
        selectedFragment = new ChiFragment();
        getFragment();
        chi.setTextColor(Color.BLUE);
        thu.setTextColor(Color.BLACK);
        leftBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        rightBar.setBackgroundColor(getResources().getColor(android.R.color.black));
        onClickAction();
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentinyear, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
    public void onClickAction(){
        lnYear.setOnClickListener(v -> showYearPickerDialog());
        chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ChiFragment();
                getFragment();
                chi.setTextColor(Color.BLUE);
                thu.setTextColor(Color.BLACK);
                leftBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                rightBar.setBackgroundColor(getResources().getColor(android.R.color.black));
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ThuFragment();
                getFragment();
                chi.setTextColor(Color.BLACK);
                thu.setTextColor(Color.BLUE);
                leftBar.setBackgroundColor(getResources().getColor(android.R.color.black));
                rightBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            }
        });
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