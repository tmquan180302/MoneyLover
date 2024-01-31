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

import com.poly.moneylover.NewItemActivity;
import com.poly.moneylover.R;
import com.poly.moneylover.models.Item;

import java.util.List;

public class ItemAdapterHorizontal extends RecyclerView.Adapter<ItemAdapterHorizontal.ItemViewHolder> {

    private List<Item> list;

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Item> data) {
        list = data;
        list.add(0, new Item("Thêm danh mục"));
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
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
        Item item = list.get(i);
        if (item.getIcon() != 0) {
            itemViewHolder.imvIcon.setVisibility(View.VISIBLE);
            itemViewHolder.imvMenu.setVisibility(View.VISIBLE);
            itemViewHolder.imvIcon.setImageResource(item.getIcon());
            itemViewHolder.imvIcon.setColorFilter(ContextCompat.getColor(itemViewHolder.imvIcon.getContext(), item.getColor()));
        } else {
            itemViewHolder.imvIcon.setVisibility(View.GONE);
            itemViewHolder.imvMenu.setVisibility(View.GONE);
        }
        itemViewHolder.tvName.setText(item.getText());

        itemViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewItemActivity.class);
            if (i != 0) intent.putExtra("item", item);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
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
