package com.poly.moneylover.ui.moneylover.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import thinhnh.fpoly.myapp.R;
import thinhnh.fpoly.myapp.adapter.AdapterListView_TrangThaiHoaDon;
import thinhnh.fpoly.myapp.csdl.DTO.TrangThaiHoaDon;
import thinhnh.fpoly.myapp.csdl.data.DataBaSe;

public class TrangThaihdFragment extends Fragment {
AdapterListView_TrangThaiHoaDon adapterListView_trangThaiHoaDon;
     ListView lisCs;
     FloatingActionButton floatCs;


     TextInputEditText tthdAdd;
     Button themtt,huytt;

     TrangThaiHoaDon trangThaiHoaDon;
    ArrayList<TrangThaiHoaDon> list = new ArrayList<>();
    public TrangThaihdFragment() {
        // Required empty public constructor
    }


    public static TrangThaihdFragment newInstance() {
        TrangThaihdFragment fragment = new TrangThaihdFragment();

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
        return inflater.inflate(R.layout.fragment_trang_thaihd, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        lisCs = (ListView) view.findViewById(R.id.lis_cs);
        floatCs = (FloatingActionButton) view.findViewById(R.id.float_cs);
        loadData();
        floatCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_trangthaihd);


                tthdAdd = (TextInputEditText) dialog.findViewById(R.id.tthd_add);
                themtt = (Button) dialog.findViewById(R.id.themtt);
                huytt = (Button) dialog.findViewById(R.id.huytt);

                themtt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (validate()){}
                        String khunggio1 = tthdAdd.getText().toString();


                        //set thuộc tính HV
                        trangThaiHoaDon  = new TrangThaiHoaDon(khunggio1);
                        //Add hv vào database
                        DataBaSe.getInstance(getActivity()).dao_tthd().insertTTHD(trangThaiHoaDon);
                        //View list hv lên màn hình
                        loadData();
                        Log.d("zzz", "onViewCreated: " + list.size());
                        dialog.dismiss();

                    }
                });
                huytt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }



    public void loadData() {
        list = (ArrayList<TrangThaiHoaDon>) DataBaSe.getInstance(getActivity()).dao_tthd().getAllTTHD();
        adapterListView_trangThaiHoaDon = new AdapterListView_TrangThaiHoaDon(getActivity(),this::loadData);
        adapterListView_trangThaiHoaDon.setdata(list);
        lisCs.setAdapter(adapterListView_trangThaiHoaDon);
    }
}