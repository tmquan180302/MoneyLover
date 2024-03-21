package com.poly.moneylover.ui.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.InYear.ChiFragment;
import com.poly.moneylover.ui.InYear.ThuFragment;
import com.poly.moneylover.ui.InYear.TongFragment;

public class InYearActivity extends AppCompatActivity {
    Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_year);


        TextView chi = findViewById(R.id.chitieu);
        TextView thu = findViewById(R.id.thunhap);
        TextView tong = findViewById(R.id.tong);

        loadFragment(new ChiFragment());
        chi.setBackgroundResource(R.drawable.backgroundtron);
        thu.setBackgroundResource(R.drawable.backgroundtron1);
        tong.setBackgroundResource(R.drawable.backgroundtron1);
        chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ChiFragment();
                loadFragment(selectedFragment);
                chi.setBackgroundResource(R.drawable.backgroundtron);
                thu.setBackgroundResource(R.drawable.backgroundtron1);
                tong.setBackgroundResource(R.drawable.backgroundtron1);
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = new ThuFragment();
                loadFragment(selectedFragment);
                chi.setBackgroundResource(R.drawable.backgroundtron1);
                thu.setBackgroundResource(R.drawable.backgroundtron);
                tong.setBackgroundResource(R.drawable.backgroundtron1);
            }
        });
        tong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment =new TongFragment();
                loadFragment(selectedFragment);
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


}