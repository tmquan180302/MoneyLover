package com.poly.moneylover.ui.bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.OtherAdapter;
import com.poly.moneylover.adapters.ServiceAdapter;
import com.poly.moneylover.interfaces.ItemOnclickService;
import com.poly.moneylover.models.Bill;
import com.poly.moneylover.models.CreateOrderZalo;
import com.poly.moneylover.models.OtherItem;
import com.poly.moneylover.models.Response.Export;
import com.poly.moneylover.models.Service;
import com.poly.moneylover.network.BillApi;
import com.poly.moneylover.network.BudgetApi;
import com.poly.moneylover.network.ServiceApi;
import com.poly.moneylover.ui.service.ServiceActivity;
import com.poly.moneylover.utils.Convert;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class BillConfirmActivity extends AppCompatActivity implements ItemOnclickService {

    private RecyclerView rcvService;

    private ImageButton imb_back;
    private Button btnPay;

    private List<Service> list;
    //    public final String amount = "10000";
    private ServiceAdapter serviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_confirm);
        initView();
        back();
        initRecycleView();
        getService();
    }

    private void getService() {

        try {
            ServiceApi.api.getListService().enqueue(new Callback<List<Service>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                    if (response.isSuccessful()) {
                        Log.d("TAG", "onResponse: " + response.body());
                        list.clear();
                        list.addAll(response.body());
                        serviceAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Service>> call, Throwable t) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pay(Service service) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        createBill(service);
    }

    private void initRecycleView() {
        list = new ArrayList<>();
        rcvService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        serviceAdapter = new ServiceAdapter(list, this);
        rcvService.setAdapter(serviceAdapter);
    }

    private void back() {

        imb_back.setOnClickListener(v -> finish());
    }

    private void createBill(Service service) {
        CreateOrderZalo orderApi = new CreateOrderZalo();

        try {
            JSONObject data = orderApi.createOrder(String.valueOf(service.getPrice()));
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(BillConfirmActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        addBill(service);
                        Intent intent = new Intent(BillConfirmActivity.this, ServiceActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Toast.makeText(BillConfirmActivity.this, "Bị hủy", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Toast.makeText(BillConfirmActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();


                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void initView() {
        imb_back = findViewById(R.id.imb_back);
        btnPay = findViewById(R.id.btnPay);
        rcvService = findViewById(R.id.rcv_service);
    }


    @Override
    public void onSelectedService(Service service) {

        btnPay.setText("Thanh toán:  " + Convert.formatNumberCurrent(String.valueOf(service.getPrice())) + " VNĐ");
        btnPay.setOnClickListener(v -> pay(service));

    }

    private void addBill(Service service) {
        Date date = new Date();

        long dayStart = date.getTime();
        try {
            Response<Void> call = BillApi.api.create(new Bill(service, dayStart)).execute();
            if (call.isSuccessful() && call.code() == 200) {
                Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onChangeBg(Service service, LinearLayout linearLayout) {

    }


}