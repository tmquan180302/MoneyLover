package com.poly.moneylover.ui.transaction;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.moneylover.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}