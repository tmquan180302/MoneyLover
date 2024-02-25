package com.poly.moneylover.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.OtherAdapter;
import com.poly.moneylover.models.OtherItem;

import java.util.ArrayList;
import java.util.List;


public class OtherFragment extends Fragment {
    private List<OtherItem> otherList;
    private RecyclerView otherRecyclerView;
    private OtherAdapter otherAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        otherList = genarateOtherItem();
        otherRecyclerView = view.findViewById(R.id.otherRecyclerView);
        otherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        otherAdapter = new OtherAdapter(otherList);
        otherRecyclerView.setAdapter(otherAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //handle event
    }

    private List<OtherItem> genarateOtherItem(){

        List<OtherItem> otherItems = new ArrayList<>();
        otherItems.add(new OtherItem(R.drawable.settings,"Cài đặt cơ bản"));
        otherItems.add(new OtherItem(R.drawable.chart_line,"Báo cáo trong năm"));
        otherItems.add(new OtherItem(R.drawable.chart_pie,"Báo cáo danh mục trong năm"));
        otherItems.add(new OtherItem(R.drawable.chart_line,"Báo cáo toàn kỳ"));
        otherItems.add(new OtherItem(R.drawable.chart_pie,"Báo cáo danh mục toàn kỳ"));
        otherItems.add(new OtherItem(R.drawable.chart_pie,"Không cho hiển thị quảng cáo"));
        otherItems.add(new OtherItem(R.drawable.chart_pie,"Đầu ra dữ liệu"));
        otherItems.add(new OtherItem(R.drawable.chart_pie,"Sao lưu dữ  liệu"));
        otherItems.add(new OtherItem(R.drawable.chart_pie,"Thông tin ứng dụng"));

        return otherItems;
    }
}