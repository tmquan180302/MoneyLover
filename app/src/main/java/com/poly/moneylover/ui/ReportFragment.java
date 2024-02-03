package com.poly.moneylover.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.TabPagerAdapter;
import com.poly.moneylover.databinding.FragmentReportBinding;
import com.poly.moneylover.ui.fragment.IncomeFragment;
import com.poly.moneylover.ui.fragment.OutcomeFragment;

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);

        configViewpager();
        return binding.getRoot();
    }

    private void configViewpager() {
        TabPagerAdapter adapterStatic = new TabPagerAdapter(requireActivity());

        binding.viewpageInput.setAdapter(adapterStatic);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewpageInput, true, true, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Chi tiêu");
                    break;
                case 1:
                    tab.setText("Thu Nhập");
                    break;
            }
        });
        tabLayoutMediator.attach();

    }

}