package com.poly.moneylover.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanphamdemo.R;
import com.example.sanphamdemo.activity.DetailItemBaiDangCongty;
import com.example.sanphamdemo.activity.SuabaidangActivity;
import com.example.sanphamdemo.interfaceall.Interface_Xoa;
import com.example.sanphamdemo.user.Ban_User;
import com.example.sanphamdemo.user.DeleteBan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//////////////////////////

public class  MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.UserViewHoler>{
    private Context context;
    ProgressDialog progressDialog;
    private ArrayList<Ban_User> arrayList;
    String selectedValue;
    public MyAdapter2(Context context) {

        this.context = context;
    }

    public void setData(ArrayList<Ban_User> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhomefragment2,parent,false);
        return new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoler holder, int position) {
     final    Ban_User item = arrayList.get(position);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Vui lòng chờ...");
        if(item == null) {
            return;
        }else {
            holder.xoaitem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressDialog.show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có chắc chắn muốn xóa bài đăng?")
                            .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://10.24.4.190:3000/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    Interface_Xoa interfaceDelete = retrofit.create(Interface_Xoa.class);
                                    Call<DeleteBan> call = interfaceDelete.DelListProduct1(item.getId());

                                    call.enqueue(new Callback<DeleteBan>() {
                                        @Override
                                        public void onResponse(Call<DeleteBan> call, Response<DeleteBan> response) {
                                            DeleteBan svrResponseDelete = response.body(); // lay kq tu serrverr
                                            Toast.makeText(context, "xóa thành công " + svrResponseDelete.getMessage(), Toast.LENGTH_SHORT).show();
                                          holder.xoaitem1.setText("Bài đăng đã được xóa");
                                            progressDialog.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<DeleteBan> call, Throwable t) {

                                            holder.xoaitem1.setText("Đã xóa");
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Từ chối", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                     progressDialog.dismiss();

                                }
                            });
                    builder.create().show();
                }
            });

            holder.addressPost1.setText(item.getDiachi().toString());
            holder.tenban1.setText(item.getTen().toString());
            holder.gio1.setText(item.getGiovao().toString());
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
            holder.gia1.setText(giatien1);

//            holder.giatien.setText(item.getGia());
                 holder.detailItem1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         onClickItem(item);
                     }
                 });


         holder.suaitem1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 onClickItemSua(item);
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
        Intent intent = new Intent(context, DetailItemBaiDangCongty.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj",request);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private void onClickItemSua(Ban_User request) {
        Intent intent = new Intent(context, SuabaidangActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj",request);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public class UserViewHoler extends RecyclerView.ViewHolder {

        private LinearLayout detailItem1;
        private LinearLayout linearHeaderPost;
        private TextView tenban1;
        private RelativeLayout bgIsNewView;
        private TextView gio1;
        private TextView gia1;
        private TextView addressPost1;
        private TextView suaitem1;
        private TextView xoaitem1;


        public UserViewHoler(@NonNull View itemView) {
            super(itemView);

            detailItem1 = (LinearLayout) itemView.findViewById(R.id.detail_item1);
            linearHeaderPost = (LinearLayout) itemView.findViewById(R.id.linearHeader_post);
            tenban1 = (TextView)itemView. findViewById(R.id.tenban1);
            bgIsNewView = (RelativeLayout)itemView. findViewById(R.id.bg_isNewView);
            gio1 = (TextView)itemView. findViewById(R.id.gio1);
            gia1 = (TextView) itemView.findViewById(R.id.gia1);
            addressPost1 = (TextView)itemView. findViewById(R.id.address_post1);
            suaitem1 = (TextView)itemView. findViewById(R.id.suaitem1);
            xoaitem1 = (TextView)itemView. findViewById(R.id.xoaitem1);

        }
    }
}

//////////////////////////////

//  holder.suaitem1.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//// Tạo dialog từ layout XML tùy chỉnh
//final Dialog customDialog = new Dialog(view.getContext());
//        customDialog.setContentView(R.layout.custom_dialog_layout);
//
//
//
//        EditText   tieudebaivietd = (EditText) customDialog.findViewById(R.id.tieudebaiviet1);
//        EditText   motacongviecd = (EditText) customDialog.findViewById(R.id.motacongviec1);
//        EditText   diachilamviecd = (EditText)  customDialog.findViewById(R.id.diachilamviec1);
//        Spinner    mucluongd = (Spinner) customDialog. findViewById(R.id.mucluong1);
//        String tieude =tieudebaivietd.getText().toString();
//        String mota = motacongviecd.getText().toString();
//        String diachi = diachilamviecd.getText().toString();
//        TextView   ok = (TextView) customDialog.findViewById(R.id.ok);
//        String[] items = {"1.000.000 - 3.000.000","3.000.000 - 5.000.000","5.000.000 - 7.000.000","7.000.000 - 10.000.000","Thương lượng"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mucluongd.setAdapter(adapter);
//        mucluongd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//@Override
//public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id1) {
//        // Xử lý khi một mục được chọn
//        selectedValue = items[position];
//        //         Toast.makeText(getContext(), selectedValue, Toast.LENGTH_SHORT).show();
//        }
//
//@Override
//public void onNothingSelected(AdapterView<?> parentView) {
//        // Xử lý khi không có mục nào được chọn
//        }
//        });
//
//        ok.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        if (selectedValue.equals("1.000.000 - 3.000.000")){
//        selectedValue = "1";
//        } else if (selectedValue.equals("3.000.000 - 5.000.000")){
//        selectedValue = "3";
//        }
//        if (selectedValue.equals("5.000.000 - 7.000.000")){
//        selectedValue = "5";
//        }
//        if (selectedValue.equals("7.000.000 - 10.000.000")){
//        selectedValue = "7";
//        }
//        else {
//        selectedValue = "10";
//        }
//        Retrofit retrofit1 = new Retrofit.Builder()
//        .baseUrl("http://192.168.1.6:3000/")
//        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
//        .build();
//
//        Interface_Update_del_ban interfaceDelete1 = retrofit1.create(Interface_Update_del_ban.class);
//        Call<UpdateBan> call1 = interfaceDelete1.updateBann(item.getId(),tieude,Integer.parseInt(selectedValue),mota,diachi);
//        call1.enqueue(new Callback<UpdateBan>() {
//@Override
//public void onResponse(Call<UpdateBan> call1, Response<UpdateBan> response) {
//        if (response.isSuccessful() && response.body() != null) {
//        Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
//        }
//        }
//
//@Override
//public void onFailure(Call<UpdateBan> call1, Throwable t) {
//        // Xử lý khi có lỗi kết nối
//        Toast.makeText(context, "Lỗi kết nối Server " + t.getMessage(), Toast.LENGTH_SHORT).show();
//        Log.d("zzzzzzzz", t.getMessage());
//
//        }
//        });
//        customDialog.dismiss();
//
//        }
//        });
//        TextView    huy = (TextView)customDialog. findViewById(R.id.huy);
//        huy.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        customDialog.dismiss();
//        }
//        });
//
//        // Hiển thị custom dialog
//        customDialog.show();
//
//
//        }
//
//        });
