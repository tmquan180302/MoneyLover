package com.poly.moneylover.Fragment.nhanvien;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import thinhnh.fpoly.myapp.R;
import thinhnh.fpoly.myapp.adapter.AdapterListView_HoaDon;
import thinhnh.fpoly.myapp.adapter.AdapterListView_HoaDonNhanVien;
import thinhnh.fpoly.myapp.csdl.DTO.HoaDon;
import thinhnh.fpoly.myapp.csdl.data.DataBaSe;

public class HoaDonFragment extends Fragment {

     ListView lisCs;
     FloatingActionButton floatCs;
     EditText tvtentimkiem;
     ImageView imgtimkiem;


    HoaDon hd;

    ArrayList<HoaDon> listhoadon = new ArrayList<>();
    AdapterListView_HoaDon adapterListView_hoaDon;
    AdapterListView_HoaDonNhanVien adapterListView_hoaDonNhanVien;



    TextView soluong;


    public HoaDonFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HoaDonFragment newInstance() {
        HoaDonFragment fragment = new HoaDonFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataa();
        soluong.setText(Integer.toString(demsoluong()));
    }

    @Override
    public void onStart() {
        super.onStart();
      //  loadDataa();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Resources res = view.getResources();
        int color = res.getColor(R.color.maudo);
        Resources resa = view.getResources();
        int colorxanh = resa.getColor(R.color.purple_700);


        imgtimkiem = view.findViewById(R.id.imgtimkiemten);
        tvtentimkiem = view.findViewById(R.id.lis_edttenhdtimkiem);
        soluong = view.findViewById(R.id.sonv1);

        lisCs = (ListView) view.findViewById(R.id.lis_cs);
        imgtimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listhoadon = (ArrayList<HoaDon>) DataBaSe.getInstance(getActivity()).dao_hoadon().gettenhoadon(tvtentimkiem.getText().toString());
                loadData();
            }
        });
        lisCs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_hoadon_chitiet);
                hd = listhoadon.get(i);
                TextView tvtenkh = (TextView) dialog.findViewById(R.id.tenchitiet);
                TextView tvsdtkh = (TextView) dialog.findViewById(R.id.sdtchitiet);
                TextView tvngaythue = (TextView) dialog.findViewById(R.id.ngaythuechitiet);
                TextView khunggio = (TextView) dialog.findViewById(R.id.khunggiochitiet);
                TextView tensan = (TextView) dialog.findViewById(R.id.tensanchitiet);
                TextView giabong = (TextView) dialog.findViewById(R.id.giabongchitiet);
                TextView giaao = (TextView) dialog.findViewById(R.id.giaaochitiet);
                TextView gianuoc = (TextView) dialog.findViewById(R.id.gianuocchitiet);
                TextView tongtien = (TextView) dialog.findViewById(R.id.tongtienchitiet);
                TextView trangthai = (TextView) dialog.findViewById(R.id.trangthaichitiet);
                tvtenkh.setText(hd.getTenkh());
                tvsdtkh.setText(hd.getSdtkh());
                tvngaythue.setText(hd.getNgaythue());
                khunggio.setText(hd.getKhunggio());
                tensan.setText(hd.getTensan());
                giabong.setText(String.valueOf(hd.getBong()));
                giaao.setText(String.valueOf(hd.getAo()));
                gianuoc.setText(String.valueOf(hd.getNuoc()));
                tongtien.setText(String.valueOf(hd.getTongtien()));
                trangthai.setText((hd.getTentrangthai()));
                if (trangthai.getText().toString().equals("Chua thanh toan")){
                    trangthai.setTextColor(color);
                }else{
                    trangthai.setTextColor(colorxanh);
                }
                Toast.makeText(getContext(), "Bạn đang xem hóa đơn khách hàng: "+ hd.getTenkh(), Toast.LENGTH_SHORT).show();
                dialog.show();
            }

        });
        floatCs = (FloatingActionButton) view.findViewById(R.id.float_cs);
        loadDataa();
        floatCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThemHoaDonActivity.class));
            }
        });

    }


    public void loadData() {
        //listhoadon = (ArrayList<HoaDon>) DataBaSe.getInstance(getActivity()).dao_hoadon().getAllHOADON();
        adapterListView_hoaDon = new AdapterListView_HoaDon(getActivity(),this::loadData);
        adapterListView_hoaDon.setdata(listhoadon);
        lisCs.setAdapter(adapterListView_hoaDon);
    }
    public void loadDataa() {
        listhoadon = (ArrayList<HoaDon>) DataBaSe.getInstance(getActivity()).dao_hoadon().getAllHOADON();
        adapterListView_hoaDon = new AdapterListView_HoaDon(getActivity(),this::loadDataa);
        adapterListView_hoaDon.setdata(listhoadon);
        lisCs.setAdapter(adapterListView_hoaDon);
    }
    public int demsoluong(){
        int x = 0;
        listhoadon =(ArrayList<HoaDon>) DataBaSe.getInstance(getActivity()).dao_hoadon().getAllHOADON();
        for(int i  = 0 ; i<listhoadon.toArray().length;i++){
            x = i+1;
        }
        return x;
    }

}