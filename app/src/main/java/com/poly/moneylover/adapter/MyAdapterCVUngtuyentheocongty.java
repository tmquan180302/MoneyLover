package com.poly.moneylover.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanphamdemo.R;
import com.example.sanphamdemo.activity.Detail_CvtuyenDung;
import com.example.sanphamdemo.user.CV_UngTuyen;

import java.util.ArrayList;

//////////////////////////

public class MyAdapterCVUngtuyentheocongty extends RecyclerView.Adapter<MyAdapterCVUngtuyentheocongty.UserViewHoler>{
    private Context context;
    private ArrayList<CV_UngTuyen> arrayList;

    public MyAdapterCVUngtuyentheocongty(Context context) {

        this.context = context;
    }

    public void setData(ArrayList<CV_UngTuyen> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemungtuyen,parent,false);
        return new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoler holder, int position) {
     final    CV_UngTuyen item = arrayList.get(position);
        if(item == null) {
            return;
        }else {

            holder.vitrituyendung.setText(item.getVi_tri_tuyen_dung().toString());
            holder.hotenungvien.setText(item.getTenungvien().toString());
            holder.emailungvien.setText(item.getEmail().toString());
            holder.sdtungvien.setText(item.getSdt().toString());
            int colorgreen = ContextCompat.getColor(context, R.color.green);
            int colorred = ContextCompat.getColor(context, R.color.red);
            int color = ContextCompat.getColor(context, R.color.lightskyblue);
            holder.trangthaicv.setText(item.getChoxetduyet().toString());
            if (holder.trangthaicv.getText().equals("Phù Hợp")){
                holder.trangthaicv.setTextColor(colorgreen);
                holder.trangthaicv.setText("Đánh giá ứng viên " +item.getChoxetduyet().toString());

            }else if (holder.trangthaicv.getText().equals("Chưa Phù Hợp")){
                holder.trangthaicv.setTextColor(colorred);
                holder.trangthaicv.setText("Đánh giá ứng viên " +item.getChoxetduyet().toString());

            }else{
                holder.trangthaicv.setTextColor(color);
            }




                 holder.detailItem1.setOnClickListener(new View.OnClickListener() {
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
    private void onClickItem(CV_UngTuyen request) {
        Intent intent = new Intent(context, Detail_CvtuyenDung.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objungtuyen",request);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public class UserViewHoler extends RecyclerView.ViewHolder {

        private LinearLayout detailItem1;
        private LinearLayout linearHeaderPost;
        private TextView vitrituyendung;
        private RelativeLayout bgIsNewView;
        private TextView hotenungvien;
        private TextView emailungvien;
        private TextView sdtungvien,trangthaicv;


        public UserViewHoler(@NonNull View itemView) {
            super(itemView);



            detailItem1 = (LinearLayout) itemView.findViewById(R.id.detail_item1);
            linearHeaderPost = (LinearLayout) itemView. findViewById(R.id.linearHeader_post);
            vitrituyendung = (TextView)  itemView.findViewById(R.id.vitrituyendung);
            bgIsNewView = (RelativeLayout) itemView. findViewById(R.id.bg_isNewView);
            hotenungvien = (TextView)  itemView.findViewById(R.id.hotenungvien);
            emailungvien = (TextView) itemView. findViewById(R.id.emailungvien);
            sdtungvien = (TextView)  itemView.findViewById(R.id.sdtungvien);
            trangthaicv = (TextView)  itemView.findViewById(R.id.trangthaicv);


        }
    }
}

//////////////////////////////


