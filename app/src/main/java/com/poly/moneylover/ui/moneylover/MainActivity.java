package com.poly.moneylover.ui.moneylover;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.ui.CalendarFragment;
import com.poly.moneylover.ui.moneylover.ui.InputFragment;
import com.poly.moneylover.ui.moneylover.ui.OtherFragment;
import com.poly.moneylover.ui.moneylover.ui.ReportFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private long Pressed;
    Toast mToast;

    private int INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(this);
        replaceFragment(new InputFragment(),1);
    }


    @Override
    public void onBackPressed() {
        if (Pressed + 2000 > System.currentTimeMillis()) {
            mToast.cancel();
            moveTaskToBack(true);
        } else {
            mToast = Toast.makeText(getApplicationContext(), "ấn 2 lần để thoát ứng dụng", Toast.LENGTH_SHORT);
            mToast.show();
        }
        Pressed = System.currentTimeMillis();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.input) {
            replaceFragment(new InputFragment(), 1);
        } else if (id == R.id.calendar) {
            replaceFragment(new CalendarFragment(), 2);
        } else if (id == R.id.report) {

            replaceFragment(new ReportFragment(), 3);
        } else if (id == R.id.other) {
            replaceFragment(new OtherFragment(), 4);
        }
        return true;
    }

    private void replaceFragment(Fragment fragment, int newIndex) {
        if (INDEX == newIndex) return;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                fragment).addToBackStack(null).commit();
        INDEX = newIndex;
    }
}