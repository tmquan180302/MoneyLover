package com.poly.moneylover.adapters;

import static java.lang.Math.round;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.constants.Constants;
import com.poly.moneylover.models.ExpenseItem;
import com.poly.moneylover.utils.Convert;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<ExpenseItem> expenseItemList;
    private Context context;
    private int type;


    public interface OnItemClickListener {
        void onItemClick(ExpenseItem expenseItem);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ReportAdapter(List<ExpenseItem> expenseItemList, Context context, int type) {
        this.expenseItemList = expenseItemList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseItem expenseItem = expenseItemList.get(position);

        Long totalValue = 1L;

        if (type == 1) {
            totalValue = Constants.TOTAL_IN;
        } else {
            totalValue = Constants.TOTAL_OUT;
        }

        try {
            holder.imageView.setImageResource(expenseItem.getImageResourceId());
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.titleTextView.setText(expenseItem.getTitle());
        String mystring = expenseItem.getPrice();
        holder.priceTextView.setText(Convert.convertNumber(Long.parseLong(expenseItem.getPrice())) + "₫");
        holder.tvPercent.setText(round((Double.parseDouble(expenseItem.getPrice()) / totalValue) * 100) + "%");

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(expenseItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView priceTextView, tvPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_view);
            titleTextView = itemView.findViewById(R.id.tv_title);
            priceTextView = itemView.findViewById(R.id.tv_price);
            tvPercent = itemView.findViewById(R.id.tv_percent);
        }
    }
}

