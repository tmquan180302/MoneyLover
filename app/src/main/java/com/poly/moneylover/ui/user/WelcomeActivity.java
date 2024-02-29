package com.poly.moneylover.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.poly.moneylover.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ImageSlider imageSlider;
    private Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        setUpFunction();
    }

    private void setUpFunction() {
        addImage();
        btnOnclick();
    }

    private void btnOnclick() {
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void addImage() {
        List<SlideModel> list = new ArrayList<>();
        list.add(new SlideModel(R.drawable.i1, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i2, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i3, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i4, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i5, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i6, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i7, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i8, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i9, ScaleTypes.FIT));
        list.add(new SlideModel(R.drawable.i10, ScaleTypes.FIT));
        imageSlider.setImageList(list);


    }

    private void initView() {
        imageSlider = findViewById(R.id.imgSlideWelcome);
        btnLogin = findViewById(R.id.btn_loginNa);
        btnRegister = findViewById(R.id.btn_register);
    }
}