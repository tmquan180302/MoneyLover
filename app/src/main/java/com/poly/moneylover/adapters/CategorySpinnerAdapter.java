package com.poly.moneylover.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.poly.moneylover.R;
import com.poly.moneylover.models.Category;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categoryList;

    public CategorySpinnerAdapter(Context context, List<Category> categoryList) {
        super(context, 0, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_category, parent, false);
        }
        Resources resources = context.getResources();

        Category category = categoryList.get(position);

        ImageView iconImageView = convertView.findViewById(R.id.img_other);
        TextView nameTextView = convertView.findViewById(R.id.tv_nameOther);

        if (category.getIcon() != 0) {
            iconImageView.setVisibility(View.VISIBLE);
            if (isValidIconResource(resources, category.getIcon())) {
                iconImageView.setImageResource(category.getIcon());
            } else {
                iconImageView.setImageResource(R.drawable.bell);
            }


            int colorId = category.getColor();
            if (isValidColorResource(resources, colorId)) {
                int color = ContextCompat.getColor(context, colorId);
                iconImageView.setColorFilter(color);
            } else {
                iconImageView.setColorFilter(category.getColor());
            }
        } else {
            iconImageView.setVisibility(View.GONE);
        }
        nameTextView.setText(category.getName());

        return convertView;
    }

    private boolean isValidColorResource(Resources resources, int colorId) {
        try {
            resources.getColor(colorId);
            return true;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }
    private boolean isValidIconResource(Resources resources, int id) {
        try {
            resources.getDrawable(id);
            return true;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }
}