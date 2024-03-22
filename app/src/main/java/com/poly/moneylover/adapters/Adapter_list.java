package com.poly.moneylover.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Dto_item;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.utils.Convert;
import com.poly.moneylover.utils.DetailitemlichActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter_list extends RecyclerView.Adapter<Adapter_list.TransactionViewHolder> {
    private List<Transaction> arrayList;

Context context;

    public Adapter_list(List<Transaction> arrayList) {
        this.arrayList = arrayList;

    }

    public Adapter_list(Context context) {
        this.context = context;
    }

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
            // Tạo một đối tượng YourDataModel (giả sử đã có dữ liệu)
            Transaction yourData = new Transaction();
            yourData.setDay(transaction.getDay()); // Gán giá trị millis cho trường "day"

            // Chuyển đổi millis sang ngày tháng năm bằng phương thức convertDayToDateString()
            String dateString = yourData.convertDayToDateString();


            // Định dạng ngày tháng năm
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDate = sdf.format(new Date(transaction.getDay()));

            // Kiểm tra nếu ngày giống với ngày trước đó, hiển thị ngày
            if (position == 0 || !currentDate.equals(sdf.format(new Date(arrayList.get(position - 1).getDay())))) {
                holder.day.setVisibility(View.VISIBLE);
                holder.day.setText(currentDate);
            } else {
                holder.day.setVisibility(View.GONE);
            }


            //  holder.day.setText(dateString);

            // Sắp xếp các ngày từ gần đến xa


            // In danh sách các ngày đã được sắp xếp
      // sortTransactionsByDate();
//            for (Transaction date : arrayList) {
//                System.out.println(date);
//                holder.day.setText(dateString);
//            }

            // Hiển thị thông tin của đối tượng
            holder.day.setText(dateString);

        holder.itemdetail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent
                Intent intent = new Intent(context, DetailitemlichActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", transaction.getTransactionId() ); // Thêm dữ liệu vào bundle
                bundle.putString("category", String.valueOf(transaction.getCategory()));
                bundle.putString("day", String.valueOf(transaction.getDay()));
                bundle.putString("note", transaction.getNote() );
                bundle.putString("price", String.valueOf(transaction.getPrice()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        }
    }

    // Sắp xếp danh sách các giao dịch theo ngày từ gần đến xa
    public void sortTransactionsByDate() {
        Collections.sort(arrayList, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                long date1 = t1.getDay();
                long date2 = t2.getDay();
                return Long.compare(date1, date2);
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }



    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemimage;
        private TextView itemdanhmuc;
        private TextView itemghichu;
        private TextView itemtien,day;

        private LinearLayout itemdetail1;



        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemimage = (ImageView) itemView.findViewById(R.id.itemimage);
            itemdanhmuc = (TextView) itemView.findViewById(R.id.itemdanhmuc);
            itemtien = (TextView) itemView.findViewById(R.id.itemtien);
            itemghichu = (TextView) itemView.findViewById(R.id.tv_note);
            day = (TextView) itemView.findViewById(R.id.day);

            itemdetail1 = (LinearLayout) itemView.findViewById(R.id.itemdetail1);

        }
    }
}



