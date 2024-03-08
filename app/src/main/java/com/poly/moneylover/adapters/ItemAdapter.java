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
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.models.Category;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Category> list = new ArrayList<>();
    private int positionSelected = 0;

    private final ItemOnclick onclick;

    public ItemAdapter(ItemOnclick onclick) {
        this.onclick = onclick;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setPositionSelected(int index) {
        notifyItemChanged(positionSelected);
        notifyItemChanged(index);
        notifyDataSetChanged();
        positionSelected = index;
        Category category = list.get(index);
        onclick.onSelectedCategory(category);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Category> data) {
        list.clear();
        list.addAll(data);
        list.add(new Category("Chỉnh sửa"));
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Category item = list.get(i);
        if (item.getIcon() != 0) {
            itemViewHolder.imvIcon.setVisibility(View.VISIBLE);
            itemViewHolder.imvIcon.setImageResource(item.getIcon());
            itemViewHolder.imvIcon.setColorFilter(ContextCompat.getColor(itemViewHolder.imvIcon.getContext(), item.getColor()));
        } else {
            itemViewHolder.imvIcon.setVisibility(View.GONE);
        }
        itemViewHolder.tvName.setText(item.getName());
        itemViewHolder.itemView.setSelected(positionSelected == i);
        itemViewHolder.itemView.setOnClickListener(v -> {
            if (i == list.size() - 1) {
                onclick.editItem();
                setPositionSelected(0);
                return;
            }
            if (positionSelected != i) {
                setPositionSelected(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imvIcon;
        private final TextView tvName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imvIcon = itemView.findViewById(R.id.imv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
