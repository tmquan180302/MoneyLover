package com.poly.moneylover.ui.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.ListFullTerm.ChiFragment;
import com.poly.moneylover.ui.ListFullTerm.ThuFragment;


public class ListFullTermActivity extends AppCompatActivity {
    private View leftBar, rightBar;
    Fragment selectedFragment = null;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_full_term);
        leftBar = findViewById(R.id.leftBar);
        rightBar = findViewById(R.id.rightBar);
        imgBack = findViewById(R.id.imgBack);
        TextView chi = findViewById(R.id.chitieu);
        TextView thu = findViewById(R.id.thunhap);

        loadFragment(new ChiFragment());
        chi.setTextColor(Color.BLUE);
        thu.setTextColor(Color.BLACK);
        leftBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        rightBar.setBackgroundColor(getResources().getColor(android.R.color.black));

        imgBack.setOnClickListener(v ->{
            finish();
        });
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