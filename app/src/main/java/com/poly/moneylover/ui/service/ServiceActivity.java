package com.poly.moneylover.ui.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.OtherAdapter;
import com.poly.moneylover.models.OtherItem;
import com.poly.moneylover.network.BillApi;
import com.poly.moneylover.ui.bill.BillConfirmActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity implements OtherAdapter.OnItemClickListener {

    private ImageButton imb_back;
    private List<OtherItem> serviceList;
    private RecyclerView serviceRecyclerView;
    private OtherAdapter serviceAdapter;

    public Boolean checkPremium = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initView();
        back();
        initRecycleView();
        checkUser();
    }


    private void checkUser() {

        Thread thread = new Thread(() -> {
            try {
                Response<Void> call = BillApi.api.checkUser().execute();
                if (call.isSuccessful() && call.code() == 200) {
                    checkPremium = true;
                } else {
                    checkPremium = false;
                }
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }


    private void initRecycleView() {
        serviceList = generateOtherItem();
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        serviceAdapter = new OtherAdapter(serviceList, this);
        serviceRecyclerView.setAdapter(serviceAdapter);
    }

    private void back() {
        imb_back.setOnClickListener(v -> finish());
    }

    private void initView() {
        imb_back = findViewById(R.id.imb_back);
        serviceRecyclerView = findViewById(R.id.rcv_service);
    }

    private List<OtherItem> generateOtherItem() {

        List<OtherItem> otherItems = new ArrayList<>();
        otherItems.add(new OtherItem(R.drawable.icon_export, "Xuất dữ liệu dạng CSV"));
        otherItems.add(new OtherItem(R.drawable.icon_pdf, "Xuất dữ liệu dạng PDF"));
        otherItems.add(new OtherItem(R.drawable.icon_restore, "Khôi phục giao dịch"));
        otherItems.add(new OtherItem(R.drawable.icon_restore, "Khôi phục danh mục"));
        return otherItems;
    }

    @Override
    public void onItemClick(OtherItem otherItem) {

        int position = serviceList.indexOf(otherItem);

        if (position == 0 && checkPremium) {
            Intent intent = new Intent(ServiceActivity.this, ExportDataCsvActivity.class);
            startActivity(intent);
        } else if (position == 1 && checkPremium) {
            Intent intent = new Intent(ServiceActivity.this, ExportDataPdfActivity.class);
            startActivity(intent);
        } else if (position == 2 && checkPremium) {
            Intent intent = new Intent(ServiceActivity.this, RestoreTransactionActivity.class);
            startActivity(intent);
        } else if (position == 3 && checkPremium) {
            Intent intent = new Intent(ServiceActivity.this, RestoreCategoryActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ServiceActivity.this, BillConfirmActivity.class);
            startActivity(intent);
        }

    }

}