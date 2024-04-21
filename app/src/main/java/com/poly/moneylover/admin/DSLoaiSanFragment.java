package com.poly.moneylover.admin;

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
import thinhnh.fpoly.myapp.adapter.AdapterListView_LoaiSan;
import thinhnh.fpoly.myapp.csdl.DTO.LoaiSan;
import thinhnh.fpoly.myapp.csdl.data.DataBaSe;

public class DSLoaiSanFragment extends Fragment {
    ListView lisCs;
    FloatingActionButton floatCs;


     TextInputEditText loaisanAdd;
     Button btnAddNV;
     Button btnHuyAddNv;

     AdapterListView_LoaiSan adapterListView_loaiSan;
     LoaiSan loaisan;
    ArrayList<LoaiSan> list = new ArrayList<>();


    public DSLoaiSanFragment() {
        // Required empty public constructor
    }


    public static DSLoaiSanFragment newInstance() {
        DSLoaiSanFragment fragment = new DSLoaiSanFragment();

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
        return inflater.inflate(R.layout.fragment_d_s_loai_san, container, false);
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
                dialog.setContentView(R.layout.dialog_add_loaisan);
                loaisanAdd = (TextInputEditText) dialog.findViewById(R.id.loaisan_add);
                btnAddNV = (Button) dialog.findViewById(R.id.btnAddNV);
                btnHuyAddNv = (Button) dialog.findViewById(R.id.btnHuyAddNv);
                btnAddNV = (Button)  dialog.findViewById(R.id.btnAddNV);
                btnHuyAddNv = (Button) dialog.findViewById(R.id.btnHuyAddNv);

               //them
                btnAddNV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                     if (validate()){}
                        String tenloai = loaisanAdd.getText().toString();

                        //set thuộc tính HV/
                        loaisan = new LoaiSan(tenloai);
                        //Add hv vào database//
                        DataBaSe.getInstance(getActivity()).dao_loaisan().insertLoaiSan(loaisan);
                        //View list hv lên màn hình
                        loadData();
                        Log.d("zzz", "onViewCreated: " + list.size());
                        dialog.dismiss();

                    }
                });

                btnHuyAddNv.setOnClickListener(new View.OnClickListener() {
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
        list = (ArrayList<LoaiSan>) DataBaSe.getInstance(getActivity()).dao_loaisan().getAllLoaiSan();
        adapterListView_loaiSan = new AdapterListView_LoaiSan(getActivity(),this::loadData);
        adapterListView_loaiSan.setdata(list);
        lisCs.setAdapter(adapterListView_loaiSan);
    }
}