package com.poly.moneylover.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.models.ExpenseItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private List<ExpenseItem> expenseItemList;
    private Context context;
    private SimpleDateFormat numberFormatFullDate = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat df = new SimpleDateFormat("dd/MM");


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
        holder.priceTextView.setText(expenseItem.getPrice() + "₫");
        if (!Objects.equals(expenseItem.getNote(), "")){
            holder.tvNote.setText("("+ expenseItem.getNote() + ")");
        }

        try {
            Date date2 = numberFormatFullDate.parse(expenseItem.getDate());
            holder.dateTextView.setText(df.format(date2));

            if (position == 0){
                holder.tvDateGroup.setText(df.format(date2.getTime()));
                holder.tvDateGroup.setVisibility(View.VISIBLE);

                return;
            }

            Date date1 = numberFormatFullDate.parse(expenseItemList.get(position - 1).getDate());
            holder.tvDateGroup.setText(df.format(date1));

            if (df.format(date1).equals(df.format(date2))){
                holder.tvDateGroup.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        TextView dateTextView;
        TextView priceTextView;
        TextView tvDateGroup, tvNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_view);
            titleTextView = itemView.findViewById(R.id.tv_title);
            dateTextView = itemView.findViewById(R.id.tv_date);
            priceTextView = itemView.findViewById(R.id.tv_price);
            tvDateGroup = itemView.findViewById(R.id.tv_date_group);
            tvNote = itemView.findViewById(R.id.tv_note);
        }
    }
}

