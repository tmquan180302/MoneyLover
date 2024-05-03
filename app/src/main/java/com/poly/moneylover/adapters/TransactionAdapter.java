package com.poly.moneylover.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.ItemOnclickTransaction;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.utils.CalendarUtils;
import com.poly.moneylover.utils.Convert;

import java.util.HashMap;
import java.util.List;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions;
    private Context context;

    private ItemOnclickTransaction itemOnclickTransaction;

    public TransactionAdapter(List<Transaction> transactions, Context context, ItemOnclickTransaction itemOnclickTransaction) {
        this.transactions = transactions;
        this.context = context;
        this.itemOnclickTransaction = itemOnclickTransaction;
    }

    private long calculateTotal(String date) {
        long totalType1 = 0;
        long totalType0 = 0;

        for (Transaction transaction : transactions) {
            if (Convert.getDayConvert(transaction.getDay()).equals(date)) {
                if (transaction.getCategory().getType() == 1) {
                    totalType1 += transaction.getPrice();
                } else if (transaction.getCategory().getType() == 0) {
                    totalType0 += transaction.getPrice();
                }
            }
        }

        return totalType1 - totalType0;
    }

    private Boolean checkDay(int position) {
        boolean isDifferentDay = true;

        if (position > 0) {

            for (int i = position - 1; i >= 0; i--) {
                Transaction prevTransaction = transactions.get(i);
                if (Convert.getDayConvert(transactions.get(position).getDay())
                        .equals(Convert.getDayConvert(prevTransaction.getDay()))) {
                    isDifferentDay = false;
                    break;
                }
            }
        }
        return isDifferentDay;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

        Transaction transaction = transactions.get(position);

        if (checkDay(position)) {
            holder.parentDayView.setVisibility(View.VISIBLE);
            holder.tvDay.setText(Convert.getDayConvert(transaction.getDay()));
            long price = calculateTotal(Convert.getDayConvert(transaction.getDay()));
            holder.tvTotal.setText(Convert.FormatNumber(price) + " đ");
        } else {
            holder.parentDayView.setVisibility(View.GONE);
        }

        holder.tvNameCategory.setText(transaction.getCategory().getName());
        holder.tvNote.setText(transaction.getNote());
        holder.tvPrice.setText(Convert.formatNumberCurrent(String.valueOf(transaction.getPrice())) + " đ");
        holder.imgIcon.setImageResource(transaction.getCategory().getIcon());
        holder.imgIcon.setColorFilter(ContextCompat.getColor(holder.imgIcon.getContext(), transaction.getCategory().getColor()));
        if (transaction.getCategory().getType() == 0){
            holder.tvPrice.setTextColor(R.color.red);
        }else {
            holder.tvPrice.setTextColor(R.color.blue);
        }
        holder.itemView.setOnClickListener(v -> {
            itemOnclickTransaction.onSelectedTransaction(transaction);
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvTotal, tvNameCategory, tvNote, tvPrice;
        ImageView imgIcon;

        LinearLayout parentDayView;

        @SuppressLint("ResourceAsColor")
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_dayTransaction);
            tvTotal = itemView.findViewById(R.id.tv_totalTransaction);
            tvNameCategory = itemView.findViewById(R.id.tv_nameCategory);
            tvNote = itemView.findViewById(R.id.tv_noteTransaction);
            tvPrice = itemView.findViewById(R.id.tv_priceTransaction);
            imgIcon = itemView.findViewById(R.id.img_transaction);
            parentDayView = itemView.findViewById(R.id.parentDayView);



        }
    }

}
