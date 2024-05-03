package com.poly.moneylover.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.ItemOnClickReport;
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.ReportModels.ReportCategoryDTO;
import com.poly.moneylover.utils.Convert;

import java.util.ArrayList;
import java.util.List;

public class ItemReportAdapter extends RecyclerView.Adapter<ItemReportAdapter.ItemReportViewHolder> {

    private List<ReportCategoryDTO> list = new ArrayList<>();
    private int positionSelected = 0;

    private final ItemOnClickReport onclick;
    private Context context;

    public ItemReportAdapter(ItemOnClickReport onclick, Context context) {
        this.onclick = onclick;
        this.context = context;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setPositionSelected(int index) {
        notifyItemChanged(positionSelected);
        notifyItemChanged(index);
        notifyDataSetChanged();
        positionSelected = index;
        ReportCategoryDTO category = list.get(index);
        onclick.onSelectdReportCategory(category);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<ReportCategoryDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report, viewGroup, false);
        return new ItemReportViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemReportViewHolder itemViewHolder, int i) {
        ReportCategoryDTO item = list.get(i);
        Resources resources = context.getResources();

        if (item.getIcon() != 0) {
            itemViewHolder.imgIcon.setVisibility(View.VISIBLE);
            itemViewHolder.imgIcon.setImageResource(item.getIcon());
            int colorId = list.get(i).getColor();
            if (isValidColorResource(resources, colorId)) {
                int color = ContextCompat.getColor(context, colorId);
                itemViewHolder.imgIcon.setColorFilter(color);
            } else {
                itemViewHolder.imgIcon.setColorFilter(item.getColor());
            }

        } else {
            itemViewHolder.imgIcon.setVisibility(View.GONE);
        }
        itemViewHolder.tvName.setText(item.getName());
        itemViewHolder.tvTotal.setText(Convert.formatNumberCurrent(String.valueOf(item.getTotal())) + "đ");
        float percent = item.getPercent();
        String percentText = String.format("%.0f", percent);
        itemViewHolder.tvPercent.setText((percent < 1) ? "<" + percentText + "%" : percentText + "%");
        itemViewHolder.itemView.setSelected(positionSelected == i);
        itemViewHolder.itemView.setOnClickListener(v -> {
            setPositionSelected(i);
        });
    }
    private boolean isValidColorResource(Resources resources, int colorId) {
        try {
            resources.getColor(colorId);
            return true;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 1;
    }

    public static class ItemReportViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgIcon;
        private final TextView tvName, tvTotal, tvPercent;

        public ItemReportViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvName = itemView.findViewById(R.id.tvName);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvPercent = itemView.findViewById(R.id.tvPercent);
        }
    }
}