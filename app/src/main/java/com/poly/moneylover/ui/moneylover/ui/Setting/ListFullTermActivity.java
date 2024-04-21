package com.poly.moneylover.ui.moneylover.ui.Setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.ui.ListFullTerm.ChiFragment;
import com.poly.moneylover.ui.moneylover.ui.ListFullTerm.ThuFragment;


public class ListFullTermActivity extends AppCompatActivity {
    private View leftBar, rightBar;  Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_full_term);
        leftBar = findViewById(R.id.leftBar);
        rightBar = findViewById(R.id.rightBar);
        TextView chi = findViewById(R.id.chitieu);
        TextView thu = findViewById(R.id.thunhap);

        loadFragment(new ChiFragment());
        chi.setTextColor(Color.BLUE);
        thu.setTextColor(Color.BLACK);
        leftBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        rightBar.setBackgroundColor(getResources().getColor(android.R.color.black));
        chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ChiFragment();
                loadFragment(selectedFragment);
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
                loadFragment(selectedFragment);
                chi.setTextColor(Color.BLACK);
                thu.setTextColor(Color.BLUE);
                leftBar.setBackgroundColor(getResources().getColor(android.R.color.black));
                rightBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
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