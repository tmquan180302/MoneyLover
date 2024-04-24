package com.poly.moneylover.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanphamdemo.R;
import com.example.sanphamdemo.user.ThongBao;

import java.util.ArrayList;

//////////////////////////

public class AdapterThongbao extends RecyclerView.Adapter<AdapterThongbao.UserViewHoler>{
    private Context context;
    private ArrayList<ThongBao> arrayList;

    public AdapterThongbao(Context context) {

        this.context = context;
    }

    public void setData(ArrayList<ThongBao> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_thong_bao,parent,false);
      return  new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoler holder, int position) {
        ThongBao item = arrayList.get(position);

        if(item == null) {
            return;
        }else {

            holder.thongbao.setText(item.getThongbao().toString());
            holder.thongbao1.setText(item.getMota().toString());





        }

    }

    @Override
    public int getItemCount() {
        if(arrayList!=null)
            return  arrayList.size();
        return 0;
    }

    public class UserViewHoler extends RecyclerView.ViewHolder {

         LinearLayout detailItem;
        private LinearLayout linearHeaderPost;
         TextView thongbao;
         TextView thongbao1;


        public UserViewHoler(@NonNull View itemView) {
            super(itemView);



            detailItem = (LinearLayout) itemView.findViewById(R.id.detail_item);
            linearHeaderPost = (LinearLayout) itemView.findViewById(R.id.linearHeader_post);
            thongbao = (TextView) itemView.findViewById(R.id.thongbao);
            thongbao1 = (TextView) itemView.findViewById(R.id.thongbao1);


        }
    }
}

//////////////////////////////


