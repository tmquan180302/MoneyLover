package com.poly.moneylover;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.poly.moneylover.ui.CalendarFragment;
import com.poly.moneylover.ui.InputFragment;
import com.poly.moneylover.ui.OtherFragment;
import com.poly.moneylover.ui.ReportFragment;

import java.util.HashMap;
import java.util.Map;

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
        replaceFragment(new InputFragment(), "InputFragment");
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


    private static final Map<Integer, String> TAG_MAP = new HashMap<Integer, String>() {{
        put(R.id.input, "InputFragment");
        put(R.id.calendar, "CalendarFragment");
        put(R.id.report, "ReportFragment");
        put(R.id.other, "OtherFragment");
    }};


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String tag = TAG_MAP.get(id);
        if (id == R.id.input) {
            replaceFragment(new InputFragment(), tag);
        } else if (id == R.id.calendar) {
            replaceFragment(new CalendarFragment(), tag);
        } else if (id == R.id.report) {
            replaceFragment(new ReportFragment(), tag);
        } else if (id == R.id.other) {
            replaceFragment(new OtherFragment(), tag);
        }
        return true;
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}