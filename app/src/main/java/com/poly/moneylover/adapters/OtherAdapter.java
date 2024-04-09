package com.poly.moneylover.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.models.OtherItem;

import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.OtherViewHolder> {

    private List<OtherItem> otherList;
    private OnItemClickListener onItemClickListener;

    public OtherAdapter(List<OtherItem> otherList, OnItemClickListener onItemClickListener) {
        this.otherList = otherList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OtherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other, parent, false);
        return new OtherViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherViewHolder holder, int position) {
        final OtherItem otherItem = otherList.get(position);
        holder.imgOther.setImageResource(otherItem.getImgOther());
        holder.nameOther.setText(otherItem.getNameOther());
    }

    public interface OnItemClickListener {
        void onItemClick(OtherItem otherItem);
    }

    @Override
    public int getItemCount() {
        return otherList.size();
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItemOther;
        ImageView imgOther;
        TextView nameOther;

        public OtherViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);

            imgOther = itemView.findViewById(R.id.img_other);
            nameOther = itemView.findViewById(R.id.tv_nameOther);
            layoutItemOther = itemView.findViewById(R.id.layout_ItemOther);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(otherList.get(position));
                    }
                }
            });
        }
    }
}