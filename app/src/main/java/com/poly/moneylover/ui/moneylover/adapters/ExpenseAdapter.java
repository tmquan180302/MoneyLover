package com.poly.moneylover.ui.moneylover.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.models.ExpenseItem;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private List<ExpenseItem> expenseItemList;
    private Context context;
    public interface OnItemClickListener {
        void onItemClick(ExpenseItem expenseItem);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public ExpenseAdapter(List<ExpenseItem> expenseItemList, Context context) {
        this.expenseItemList = expenseItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_out, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseItem expenseItem = expenseItemList.get(position);

        holder.imageView.setImageResource(expenseItem.getImageResourceId());
        holder.titleTextView.setText(expenseItem.getTitle());
        holder.dateTextView.setText(expenseItem.getDate());
        holder.priceTextView.setText(expenseItem.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(expenseItem);
                }
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
        TextView dateTextView;
        TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_view);
            titleTextView = itemView.findViewById(R.id.tv_title);
            dateTextView = itemView.findViewById(R.id.tv_date);
            priceTextView = itemView.findViewById(R.id.tv_price);
        }
    }
}

