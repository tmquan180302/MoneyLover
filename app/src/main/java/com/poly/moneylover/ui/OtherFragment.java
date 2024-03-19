package com.poly.moneylover.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.OtherAdapter;
import com.poly.moneylover.models.OtherItem;
import com.poly.moneylover.ui.Setting.BasicActivity;
import com.poly.moneylover.ui.Setting.FullTermActivity;
import com.poly.moneylover.ui.Setting.InYearActivity;
import com.poly.moneylover.ui.Setting.ListFullTermActivity;
import com.poly.moneylover.ui.Setting.ListInYearActivity;

import java.util.ArrayList;
import java.util.List;


public class OtherFragment extends Fragment implements OtherAdapter.OnItemClickListener{
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
        otherAdapter = new OtherAdapter(otherList, this);
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

    @Override
    public void onItemClick(OtherItem otherItem) {
        int position = otherList.indexOf(otherItem);
        if (position == 0){
            Intent intent = new Intent(requireContext(), BasicActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(requireContext(), InYearActivity.class);
            startActivity(intent);
        }
        else if (position == 2) {
            Intent intent = new Intent(requireContext(), ListInYearActivity.class);
            startActivity(intent);
        }
        else if (position == 3) {
            Intent intent = new Intent(requireContext(), FullTermActivity.class);
            startActivity(intent);
        }
        else if (position == 4) {
            Intent intent = new Intent(requireContext(), ListFullTermActivity.class);
            startActivity(intent);
        }
    }
}