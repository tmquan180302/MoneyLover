package com.poly.moneylover.ui.moneylover.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.interfaces.ColorOnClick;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private List<Integer> list;

    private ColorOnClick colorOnClick;

    public ColorAdapter(ColorOnClick colorOnClick) {
        this.colorOnClick = colorOnClick;
    }

    private int positionSelected = 0;

    public void setPositionSelected(int positon) {
        notifyItemChanged(positionSelected);
        notifyItemChanged(positon);
        positionSelected = positon;
        colorOnClick.ColorSelected(list.get(positon));
    }



    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Integer> data) {
        list = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color, viewGroup, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder iconViewHolder, int i) {
        int colorId = list.get(i);
        iconViewHolder.imvColor.setImageResource(colorId);
        iconViewHolder.itemView.setSelected(positionSelected == i);
        iconViewHolder.itemView.setOnClickListener(v -> setPositionSelected(i));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imvColor;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            imvColor = itemView.findViewById(R.id.imv_color);
        }
    }
}
