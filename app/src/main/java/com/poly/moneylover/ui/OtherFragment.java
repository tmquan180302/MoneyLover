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
import android.widget.Toast;

import com.poly.moneylover.R;
import com.poly.moneylover.adapters.OtherAdapter;
import com.poly.moneylover.models.OtherItem;
import com.poly.moneylover.network.BillApi;
import com.poly.moneylover.ui.Setting.BasicActivity;
import com.poly.moneylover.ui.Setting.FullTermActivity;
import com.poly.moneylover.ui.Setting.InYearActivity;
import com.poly.moneylover.ui.Setting.ListFullTermActivity;
import com.poly.moneylover.ui.Setting.ListInYearActivity;
import com.poly.moneylover.ui.bill.BillConfirmActivity;
import com.poly.moneylover.ui.service.ExportDataCsvActivity;
import com.poly.moneylover.ui.service.RestoreTransactionActivity;
import com.poly.moneylover.ui.service.ServiceActivity;
import com.poly.moneylover.ui.user.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.HttpException;
import retrofit2.Response;


public class OtherFragment extends Fragment implements OtherAdapter.OnItemClickListener {
    private List<OtherItem> otherList;
    private RecyclerView otherRecyclerView;
    private OtherAdapter otherAdapter;

    public Boolean checkPremium = false;

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
        checkUser();
        //handle event
    }

    private void checkUser() {

        Thread thread = new Thread(() -> {
            try {
                Response<Void> call = BillApi.api.checkUser().execute();
                if (call.isSuccessful() && call.code() == 200) {
                    checkPremium = true;
                } else {
                    checkPremium = false;
                }
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

    private List<OtherItem> genarateOtherItem() {

        List<OtherItem> otherItems = new ArrayList<>();
        otherItems.add(new OtherItem(R.drawable.icon_setting, "Cài đặt cơ bản"));//0
        otherItems.add(new OtherItem(R.drawable.icon_statistical, "Báo cáo trong năm"));//1
        otherItems.add(new OtherItem(R.drawable.icon_pie, "Báo cáo danh mục trong năm"));//2
        otherItems.add(new OtherItem(R.drawable.icon_statistical, "Báo cáo toàn kỳ"));//3
        otherItems.add(new OtherItem(R.drawable.icon_pie, "Báo cáo danh mục toàn kỳ"));//4
        otherItems.add(new OtherItem(R.drawable.icon_premium, "Dịch vụ premium"));//5
        otherItems.add(new OtherItem(R.drawable.icon_export, "Xuất dữ liệu (Premium)"));//6
        otherItems.add(new OtherItem(R.drawable.icon_restore, "Khôi phục dữ liệu (Premium)"));//7
        otherItems.add(new OtherItem(R.drawable.icon_infomation, "Thông tin ứng dụng"));//8
        otherItems.add(new OtherItem(R.drawable.icon_logout, "Thoát ứng dụng"));//9

        return otherItems;
    }

    @Override
    public void onItemClick(OtherItem otherItem) {
        int position = otherList.indexOf(otherItem);
        if (position == 0) {
            Intent intent = new Intent(requireContext(), BasicActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(requireContext(), InYearActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(requireContext(), ListInYearActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(requireContext(), FullTermActivity.class);
            startActivity(intent);
        } else if (position == 4) {
            Intent intent = new Intent(requireContext(), ListFullTermActivity.class);
            startActivity(intent);
        } else if (position == 5) {
            Intent intent = new Intent(requireContext(), ServiceActivity.class);
            startActivity(intent);
        } else if (position == 6 && checkPremium) {
            Intent intent = new Intent(requireContext(), ExportDataCsvActivity.class);
            startActivity(intent);
        } else if (position == 7 && checkPremium) {
            Intent intent = new Intent(requireContext(), RestoreTransactionActivity.class);
            startActivity(intent);
        } else if (position == 8) {
            Toast.makeText(requireContext(), "Not Done", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(requireContext(), BillConfirmActivity.class);
//            startActivity(intent);
        } else if (position == 9) {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(requireContext(), BillConfirmActivity.class);
            startActivity(intent);
        }
    }
}