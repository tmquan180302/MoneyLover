package com.poly.moneylover.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.interfaces.DeleteCategory;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.Transaction;
import com.poly.moneylover.ui.category.NewItemActivity;
import com.poly.moneylover.R;

import java.util.List;

public class ItemAdapterHorizontal extends RecyclerView.Adapter<ItemAdapterHorizontal.ItemViewHolder> {

    private List<Category> list;

    private DeleteCategory deleteCategory;

    public ItemAdapterHorizontal(DeleteCategory deleteCategory) {
        this.deleteCategory = deleteCategory;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Category> data) {
        list = data;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        Category category = list.get(position);
        list.remove(position);
        notifyItemRemoved(position);
        deleteCategory.delete(position, category);
    }

    public void insertCategory(int position, Category category) {
        list.add(position, category);
        notifyItemInserted(position);
    }
    public void insertTransaction(int position, Transaction transaction) {
        list.add(position, transaction.getCategory());
        notifyItemInserted(position);
    }

    public void refreshItem(int position) {
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horizontal, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Category item = list.get(i);
        itemViewHolder.imvIcon.setVisibility(View.VISIBLE);
        itemViewHolder.imvMenu.setVisibility(View.VISIBLE);
        itemViewHolder.imvIcon.setImageResource(item.getIcon());
        itemViewHolder.imvIcon.setColorFilter(ContextCompat.getColor(itemViewHolder.imvIcon.getContext(), item.getColor()));
        itemViewHolder.tvName.setText(item.getName());
        itemViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewItemActivity.class);
            intent.putExtra("item", item);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imvIcon;
        private final ImageView imvMenu;
        private final TextView tvName;
        LinearLayout lnForeground;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imvIcon = itemView.findViewById(R.id.imv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            imvMenu = itemView.findViewById(R.id.imv_menu);
            lnForeground = itemView.findViewById(R.id.ln_foreground);
        }
    }
}
