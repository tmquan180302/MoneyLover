package com.poly.moneylover.ui.moneylover.ui.ThuChiCoDinh;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.poly.moneylover.R;


public class ThemSuaActivity extends AppCompatActivity {
    Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua);
        TextView chi = findViewById(R.id.chitieu);
        TextView thu = findViewById(R.id.thunhap);

        loadFragment(new ChiFragment());
        chi.setTextColor(Color.BLUE);
        thu.setTextColor(Color.BLACK);

        chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ChiFragment();
                loadFragment(selectedFragment);
                chi.setTextColor(Color.BLUE);
                thu.setTextColor(Color.BLACK);

            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ThuFragment();
                loadFragment(selectedFragment);
                chi.setTextColor(Color.BLACK);
                thu.setTextColor(Color.BLUE);

            }
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentinyear, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}