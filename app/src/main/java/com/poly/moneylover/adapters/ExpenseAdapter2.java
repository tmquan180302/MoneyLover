package com.poly.moneylover.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.models.ExpenseItem2;
import com.poly.moneylover.utils.Convert;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExpenseAdapter2 extends RecyclerView.Adapter<ExpenseAdapter2.ViewHolder> {
    private List<ExpenseItem2> expenseItemList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(ExpenseItem2 expenseItem);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ExpenseAdapter2(List<ExpenseItem2> expenseItemList, Context context) {
        this.expenseItemList = expenseItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_out, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // dung chua
        Collections.sort(expenseItemList, (item1, item2) -> item2.getDate().compareTo(item1.getDate()));
        ExpenseItem2 expenseItem = expenseItemList.get(position);
        Resources resources = context.getResources();

        if (expenseItem.getExpenseItem().getId() != null){
            holder.tvDateGroup.setVisibility(View.GONE);
            holder.rl.setVisibility(View.VISIBLE);

            holder.imageView.setImageResource(expenseItem.getExpenseItem().getImageResourceId());
            int colorId = expenseItem.getExpenseItem().getColor();
            if (isValidColorResource(resources, colorId)) {
                int color = ContextCompat.getColor(context, colorId);
                holder.imageView.setColorFilter(color);
            } else {
                holder.imageView.setColorFilter(expenseItem.getExpenseItem().getColor());
            }
            holder.titleTextView.setText(expenseItem.getExpenseItem().getTitle());
            holder.priceTextView.setText(Convert.convertNumber(Long.parseLong(expenseItem.getExpenseItem().getPrice())) + "₫");
            if (!Objects.equals(expenseItem.getExpenseItem().getNote(), "")){
                holder.tvNote.setText("("+ expenseItem.getExpenseItem().getNote() + ")");
            }
            String tempS = expenseItem.getExpenseItem().getDate();
            tempS = tempS.substring(0, 5);
            holder.dateTextView.setText(tempS);

            holder.itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(expenseItem);
                }
            });

        }else {
            holder.tvDateGroup.setVisibility(View.VISIBLE);
            holder.rl.setVisibility(View.GONE);
            Long tempTotal = expenseItem.getExpenseItem().getTotal();
            // 2000 --> 2,000
            holder.tvDateGroup.setText(expenseItem.getDate() + "(" + Convert.convertNumber(tempTotal) + "₫)");
//            holder.tvDateGroup.setText(expenseItem.getDate() + "(" + expenseItem.getExpenseItem().getTotal() + "₫)");
//            holder.tvDateGroup.setText(expenseItem.getDate());
        }
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
        RelativeLayout rl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_view);
            titleTextView = itemView.findViewById(R.id.tv_title);
            dateTextView = itemView.findViewById(R.id.tv_date);
            priceTextView = itemView.findViewById(R.id.tv_price);
            tvDateGroup = itemView.findViewById(R.id.tv_date_group);
            tvNote = itemView.findViewById(R.id.tv_note);
            rl = itemView.findViewById(R.id.rl_content);
        }
    }
    private boolean isValidColorResource(Resources resources, int colorId) {
        try {
            resources.getColor(colorId);
            return true;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }
}

