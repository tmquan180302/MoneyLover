package com.poly.moneylover.adapters;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.utils.Convert;

import java.util.List;

public class Adapter_list extends RecyclerView.Adapter<Adapter_list.TransactionViewHolder> {
    private List<Transaction> arrayList;


    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Transaction> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistchitieu, parent, false);
        return new TransactionViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = arrayList.get(position);
        if (transaction != null) {
            holder.itemimage.setImageResource(transaction.getCategory().getIcon());
            holder.itemimage.setColorFilter(ContextCompat.getColor(holder.itemimage.getContext(), transaction.getCategory().getColor()));
            holder.itemtien.setText(Convert.FormatNumber(transaction.getPrice())+"đ");
            if (transaction.getCategory().getType() != 0){
                holder.itemtien.setTextColor(ContextCompat.getColor(holder.itemtien.getContext(), R.color.blue_sky));
            }
            holder.itemdanhmuc.setText(transaction.getCategory().getName());
            holder.itemghichu.setText(transaction.getNote());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

//    private void onClickItem(Dto_item request) {
//        Intent intent = new Intent(context, DetailitemlichActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("obj", request);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemimage;
        private TextView itemdanhmuc;
        private TextView itemghichu;
        private TextView itemtien;


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemimage = (ImageView) itemView.findViewById(R.id.itemimage);
            itemdanhmuc = (TextView) itemView.findViewById(R.id.itemdanhmuc);
            itemtien = (TextView) itemView.findViewById(R.id.itemtien);
            itemghichu = (TextView) itemView.findViewById(R.id.tv_note);
        }
    }
}



