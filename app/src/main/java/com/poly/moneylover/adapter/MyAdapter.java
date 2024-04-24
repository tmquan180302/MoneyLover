package com.poly.moneylover.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanphamdemo.R;
import com.example.sanphamdemo.activity.DetailItemBaiDang;
import com.example.sanphamdemo.interfaceall.Interface_ListBan;
import com.example.sanphamdemo.interfaceall.Interface_Sua;
import com.example.sanphamdemo.user.Ban_User;
import com.example.sanphamdemo.user.UpdateBan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//////////////////////////

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.UserViewHoler>{
    private Context context;
    private ArrayList<Ban_User> arrayList;

    public MyAdapter(Context context) {

        this.context = context;

    }

    public void setData(ArrayList<Ban_User> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhomefragment,parent,false);
        return new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoler holder, int position) {
     final    Ban_User item = arrayList.get(position);
    //    UngVien ungVien = dataListungvien.get(position);

        if(item == null) {
            return;
        }else {

            holder.btnaddlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (item.getTrangthai() ==1){
                        holder.btnaddlist.setBackgroundResource(R.drawable.heart_icon_xam);

                        String trangthai = "0";
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.24.4.190:3000/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Interface_Sua interfaceUpdate = retrofit.create(Interface_Sua.class);
                        Call<UpdateBan> call = interfaceUpdate.updateBan(item.getId(), 0);

                        call.enqueue(new Callback<UpdateBan>() {
                            @Override
                            public void onResponse(Call<UpdateBan> call, Response<UpdateBan> response) {
                                UpdateBan svrResponseUpdate = response.body();

                                //   Toast.makeText(context, "Đã sửa thành công!!!"+svrResponseUpdate.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<UpdateBan> call, Throwable t) {

                                //        Toast.makeText(Test.this, "Đã lỗi sửa!!!"+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Retrofit retrofit1 = new Retrofit.Builder()
                                .baseUrl("http://10.24.4.190:3000/") // Điền địa chỉ cơ sở của API của bạn
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        // Tạo instance của ApiService từ Retrofit
                        Interface_ListBan apiService = retrofit1.create(Interface_ListBan.class);

                        // Gọi API để lấy dữ liệu
                        Call<List<Ban_User>> call1 = apiService.getListProduct1();
                        call1.enqueue(new Callback<List<Ban_User>>() {
                            @Override
                            public void onResponse(Call<List<Ban_User>> call1, Response<List<Ban_User>> response) {
                                // Xử lý dữ liệu khi nhận được
                                List<Ban_User> responseData = response.body();

                                // Cập nhật dataList và thông báo thay đổi trong Adapter

                            }

                            @Override
                            public void onFailure(Call<List<Ban_User>> call1, Throwable t) {
                                // Xử lý khi có lỗi xảy ra
                                Log.e("MainActivity", "Error: " + t.getMessage());
                            }
                        });
                    }
                    if (item.getTrangthai() ==0){
                        holder.btnaddlist.setBackgroundResource(R.drawable.likeheart);

                        String trangthai = "1";
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.24.4.190:3000/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Interface_Sua interfaceUpdate = retrofit.create(Interface_Sua.class);
                        Call<UpdateBan> call = interfaceUpdate.updateBan(item.getId(), Integer.parseInt(trangthai));

                        call.enqueue(new Callback<UpdateBan>() {
                            @Override
                            public void onResponse(Call<UpdateBan> call, Response<UpdateBan> response) {
                                UpdateBan svrResponseUpdate = response.body();

                                //   Toast.makeText(context, "Đã sửa thành công!!!"+svrResponseUpdate.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<UpdateBan> call, Throwable t) {

                                //        Toast.makeText(Test.this, "Đã lỗi sửa!!!"+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Retrofit retrofit2 = new Retrofit.Builder()
                                .baseUrl("http://10.24.4.190:3000/") // Điền địa chỉ cơ sở của API của bạn
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        // Tạo instance của ApiService từ Retrofit
                        Interface_ListBan apiService = retrofit2.create(Interface_ListBan.class);

                        // Gọi API để lấy dữ liệu
                        Call<List<Ban_User>> call2 = apiService.getListProduct1();
                        call2.enqueue(new Callback<List<Ban_User>>() {
                            @Override
                            public void onResponse(Call<List<Ban_User>> call2, Response<List<Ban_User>> response) {
                                // Xử lý dữ liệu khi nhận được
                                List<Ban_User> responseData = response.body();

                                // Cập nhật dataList và thông báo thay đổi trong Adapter

                            }

                            @Override
                            public void onFailure(Call<List<Ban_User>> call2, Throwable t) {
                                // Xử lý khi có lỗi xảy ra
                                Log.e("MainActivity", "Error: " + t.getMessage());
                            }
                        });
                    }
                }
            });
            holder.tenbann.setText(item.getTen().toString());
            holder.gioo.setText(item.getGiovao().toString());
            String giatien1 ="" ;
            if (item.getGia() ==1)
            {
                giatien1 ="1.000.000-3.000.000 Đ";
            }
            if (item.getGia() ==3)
            {
                giatien1 ="3.000.000-5.000.000 Đ";
            }
            if (item.getGia() ==5)
            {
                giatien1 ="5.000.000-7.000.000 Đ";
            }
            if (item.getGia() ==7)
            {
                giatien1 ="7.000.000-10.000.000 Đ";
            }
            if (item.getGia() ==10)
            {
                giatien1 ="Thương Lượng";
            }
            holder.giatien.setText(giatien1);

//            holder.giatien.setText(item.getGia());
                 holder.detail_item.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         onClickItem(item);
                     }
                 });



        }

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return  arrayList.size();
        return 0;
    }
    private void onClickItem(Ban_User request) {
        Intent intent = new Intent(context, DetailItemBaiDang.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj",request);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public class UserViewHoler extends RecyclerView.ViewHolder {

        LinearLayout detail_item;
        TextView tenbann, gioo, giatien;
         TextView btnaddlist;

        public UserViewHoler(@NonNull View itemView) {
            super(itemView);
    btnaddlist = itemView.findViewById(R.id.btn_add_to_list1);
            detail_item =itemView.findViewById(R.id.detail_item);
            tenbann = itemView.findViewById(R.id.tenban);
            gioo = itemView.findViewById(R.id.gio);
            giatien = itemView.findViewById(R.id.gia);

        }
    }
}

//////////////////////////////


