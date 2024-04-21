package com.poly.moneylover.ui.moneylover.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.ui.moneylover.models.OtherItem;

import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.OtherViewHolder>{
    private List<OtherItem> otherList;

    public OtherAdapter(List<OtherItem> otherList) {
        this.otherList = otherList;
    }

    @NonNull
    @Override
    public OtherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other, parent, false);
        return new OtherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherViewHolder holder, int position) {
        final OtherItem otherItem = otherList.get(position);
        holder.imgOther.setImageResource(otherItem.getImgOther());
        holder.nameOther.setText(otherItem.getNameOther());

    }

    @Override
    public int getItemCount() {
        return otherList.size();
    }

    public static class OtherViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutItemOther;
        ImageView imgOther;
        TextView nameOther;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOther = itemView.findViewById(R.id.img_other);
            nameOther = itemView.findViewById(R.id.tv_nameOther);
            layoutItemOther = itemView.findViewById(R.id.layout_ItemOther);
        }
    }
}
