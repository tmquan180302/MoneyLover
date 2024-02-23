package com.poly.moneylover.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.models.Dto_item;
import com.poly.moneylover.models.Item;
import com.poly.moneylover.ui.InputFragment;
import com.poly.moneylover.utils.DetailitemlichActivity;

import java.util.ArrayList;
import java.util.List;

public class Adapter_list extends RecyclerView.Adapter<Adapter_list.UserViewHoler>{
    private  List<Item> itemList;

    public Adapter_list(List<Item> itemList) {
        this.itemList = itemList;
    }
    private Context context;
    private ArrayList<Dto_item> arrayList;

    public Adapter_list(Context context) {

        this.context = context;

    }

    public void setData(ArrayList<Dto_item> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistchitieu,parent,false);
        return new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoler holder, int position) {
     final    Dto_item item = arrayList.get(position);



        if(item == null) {
            return;
        }else {
            // Lấy giá trị từ item
            int danhMuc = item.getDanhmuc();
            String name = "";
            if (danhMuc ==0){

                name = "Ăn uống";
              holder.itemimage.setImageResource(R.drawable.plate_and_utensils);
                int color = context.getResources().getColor(R.color.orange); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }
            if (danhMuc ==1){
                name = "Chi tiêu hàng ngày";
                holder.itemimage.setImageResource(R.drawable.icon_liquid);
                int color = context.getResources().getColor(R.color.green); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }
            if (danhMuc ==2){
                name = "Quần áo";
                holder.itemimage.setImageResource(R.drawable.icon_shirt);
                int color = context.getResources().getColor(R.color.blue); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }
            if (danhMuc ==3){
                name = "Mỹ phẩm";
                holder.itemimage.setImageResource(R.drawable.icon_lipstick);
                int color = context.getResources().getColor(R.color.red); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }
            if (danhMuc ==4){
                name = "Phí giao lưu";
                holder.itemimage.setImageResource(R.drawable.icon_coffee);
                int color = context.getResources().getColor(R.color.yellow); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }
            if (danhMuc ==5){
                name = "Y tế";
                holder.itemimage.setImageResource(R.drawable.icon_medicine);
                int color = context.getResources().getColor(R.color.red); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }
            if (danhMuc ==6){
                name = "Giáo dục";
                holder.itemimage.setImageResource(R.drawable.icon_education);
                int color = context.getResources().getColor(R.color.green1); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            } if (danhMuc ==7){
                name = "Tiền điện";
                holder.itemimage.setImageResource(R.drawable.icon_tap_faucet);
                int color = context.getResources().getColor(R.color.blue_sky); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }
            if (danhMuc ==8){
                name = "Phí đi lại";
                holder.itemimage.setImageResource(R.drawable.icon_subway);
                int color = context.getResources().getColor(R.color.brown); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }

            if (danhMuc ==9){
                name = "Phí liên lạc";
                holder.itemimage.setImageResource(R.drawable.icon_smartphone);
                int color = context.getResources().getColor(R.color.color_text_1); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }

            if (danhMuc ==10){
                name = "Tiền nhà";
                holder.itemimage.setImageResource(R.drawable.icon_house);
                int color = context.getResources().getColor(R.color.orange); // Thay R.color.orange bằng resource màu mong muốn
                holder.itemimage.setColorFilter(color);
            }



            String ghiChu = item.getGhichu();

// Tạo một SpannableString từ giá trị danhMuc
            SpannableString spannableString = new SpannableString(name);

// Kiểm tra và thêm ghiChu vào spannableString nếu không rỗng
            if (ghiChu != null && !ghiChu.isEmpty()) {
                String ghiChuText = " (" + ghiChu + ")";

                // Tạo một SpannableString cho phần ghiChu và đặt màu sắc
                SpannableString ghiChuSpannable = new SpannableString(ghiChuText);
                ghiChuSpannable.setSpan(new ForegroundColorSpan(R.color.color_text_2), 0, ghiChuText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                // Ghép spannableString và ghiChuSpannable lại với nhau
                spannableString = new SpannableString(TextUtils.concat(name, ghiChuSpannable));

            }

// Đặt văn bản đã tạo vào TextView

                holder.itemdanhmuc.setText(spannableString);


            holder.itemtien.setText(item.getTien().toString()+"đ");
            holder.itemdetail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItem(item);
                }
            });

            holder.itemdetail.setOnClickListener(new View.OnClickListener() {
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
    private void onClickItem(Dto_item request) {
        Intent intent = new Intent(context, DetailitemlichActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj",request);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public class UserViewHoler extends RecyclerView.ViewHolder {

        private LinearLayout itemdetail1;
        private ImageView itemimage;
        private TextView itemdanhmuc;
        private TextView itemghichu;
        private TextView itemtien;
        private ImageButton itemdetail;




        public UserViewHoler(@NonNull View itemView) {
            super(itemView);
            itemdetail1 = (LinearLayout) itemView.findViewById(R.id.itemdetail1);
            itemimage = (ImageView)  itemView.findViewById(R.id.itemimage);
            itemdanhmuc = (TextView) itemView. findViewById(R.id.itemdanhmuc);

            itemtien = (TextView)  itemView.findViewById(R.id.itemtien);
            itemdetail =  itemView. findViewById(R.id.itemdetail);

        }
    }
}

//////////////////////////////


