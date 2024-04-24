package com.poly.moneylover.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sanphamdemo.fragment.UngTuyenFragment;

public class Adapter_of_menu extends FragmentStateAdapter{



    public Adapter_of_menu(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
              //  fragment = CongTyFragment.newInstance();
                 break;
            case 1:
                fragment = UngTuyenFragment.newInstance();
                break;

        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2; // số trang
    }
}
