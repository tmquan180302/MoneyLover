package com.poly.moneylover.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sanphamdemo.R;
import com.example.sanphamdemo.user.UserMonAn;

import java.util.ArrayList;

public class AdapterMonan extends BaseAdapter {
    private Context context;
    private ArrayList<UserMonAn> arrayList;
    private int layout;

    public AdapterMonan(Context context, ArrayList<UserMonAn> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserViewHolder userViewHolder = null;
        if (view == null) {
            userViewHolder = new UserViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(layout, null);
            userViewHolder.tenmon = (TextView)  view.findViewById(R.id.tenmon);
            userViewHolder.giamon = (TextView) view.findViewById(R.id.giamon);
            userViewHolder.giamsl = (TextView) view.findViewById(R.id.giamsl);
            userViewHolder. slmon = (TextView) view.findViewById(R.id.slmon);
            userViewHolder.tangsl = (TextView) view.findViewById(R.id.tangsl);

            view.setTag(userViewHolder);

        } else {
            userViewHolder = (UserViewHolder) view.getTag();
        }


        userViewHolder.tenmon.setText(arrayList.get(i).getTenmonan());

        userViewHolder.giamon.setText(arrayList.get(i).getGiamon()+"Đ");
        userViewHolder.slmon.setText(arrayList.get(i).getSoluongmon());
        return view;
    }

    public static class UserViewHolder {

         TextView tenmon;
         TextView giamon;
         TextView giamsl;
         TextView slmon;
         TextView tangsl;



    }
}
