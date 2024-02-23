package com.poly.moneylover.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.poly.moneylover.ui.transaction.fragment.IncomeFragment;
import com.poly.moneylover.ui.transaction.fragment.OutcomeFragment;

public class TabPagerAdapter extends FragmentStateAdapter {

    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new OutcomeFragment();
            case 1:
                return new IncomeFragment();
            default:
                return new OutcomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

