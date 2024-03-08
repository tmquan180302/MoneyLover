package com.poly.moneylover.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.IconOnClick;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    private List<Integer> list;
    private int positionSelected = 0;

    private int colorSelectd;

    private IconOnClick iconOnClick;

    public IconAdapter(IconOnClick iconOnClick) {
        this.iconOnClick = iconOnClick;
    }

    public void changeColor(int newColor) {
        colorSelectd = newColor;
        notifyItemChanged(positionSelected);
    }

    public void setPositionSelected(int positon) {
        notifyItemChanged(positionSelected);
        notifyItemChanged(positon);
        positionSelected = positon;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Integer> data) {
        list = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.icon, viewGroup, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder iconViewHolder, int i) {
        int iconId = list.get(i);
        iconViewHolder.imvIcon.setImageResource(iconId);
        if (positionSelected == i && colorSelectd != 0)
            iconViewHolder.imvIcon.setColorFilter(ContextCompat.getColor(iconViewHolder.imvIcon.getContext(), colorSelectd));
        else
            iconViewHolder.imvIcon.setColorFilter(ContextCompat.getColor(iconViewHolder.imvIcon.getContext(), R.color.color_text_1));
        iconViewHolder.itemView.setSelected(positionSelected == i);
        iconViewHolder.itemView.setOnClickListener(v -> {
            setPositionSelected(i);
            iconOnClick.iconSelected(iconId);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class IconViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imvIcon;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            imvIcon = itemView.findViewById(R.id.imv_icon);
        }
    }
}
