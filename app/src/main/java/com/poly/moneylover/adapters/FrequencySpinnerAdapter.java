package com.poly.moneylover.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FrequencySpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;
    private List<String> serverValues;

    public FrequencySpinnerAdapter(Context context, List<String> items, List<String> serverValues) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        this.serverValues = serverValues;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getDisplayText(position));

        return convertView;
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    public String getDisplayText(int position) {
        return items.get(position);
    }

    public String getServerValue(int position) {
        return serverValues.get(position);
    }
}
