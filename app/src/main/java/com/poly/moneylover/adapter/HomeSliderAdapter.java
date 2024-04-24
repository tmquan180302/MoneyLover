package com.poly.moneylover.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanphamdemo.R;

import java.util.List;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.SliderViewHolder> {
    private List<Integer> images;

    public HomeSliderAdapter(List<Integer> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        int imageRes = images.get(position);
        holder.imageView.setImageResource(imageRes);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(
                    R.id.img_slider);
        }
    }
}
