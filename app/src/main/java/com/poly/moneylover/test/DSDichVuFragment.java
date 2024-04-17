package com.poly.moneylover.test;

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
import thinhnh.fpoly.myapp.adapter.AdapterListView_DichVu;
import thinhnh.fpoly.myapp.csdl.DTO.DichVu;
import thinhnh.fpoly.myapp.csdl.data.DataBaSe;


public class DSDichVuFragment extends Fragment {

//lllllllllllll
     ListView lisCs;
     FloatingActionButton floatCs;

    ArrayList<DichVu> list = new ArrayList<>();
   DichVu dichVu;
     TextInputEditText tendv;
     TextInputEditText giadv;
     Button btnAdddv;
     Button btnHuyAdddv;

     AdapterListView_DichVu adapterListView_dichVu;

//kkkkkkkkkkkkkkkkk
    public DSDichVuFragment() {
        // Required empty public constructor
    }


    public static DSDichVuFragment newInstance() {
        DSDichVuFragment fragment = new DSDichVuFragment();

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
        return inflater.inflate(R.layout.fragment_d_s_dich_vu, container, false);
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
                dialog.setContentView(R.layout.dialog_add_dichvu);


                tendv = (TextInputEditText) dialog.findViewById(R.id.tendv);
                giadv = (TextInputEditText) dialog.findViewById(R.id.giadv);
                btnAdddv = (Button) dialog.findViewById(R.id.btnAddSan);
                btnHuyAdddv = (Button) dialog.findViewById(R.id.btnHuyAddSan);



                btnAdddv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (validate()){}
                        String tendv1 = tendv.getText().toString();
                        String giadv1 = giadv.getText().toString();

                        //set thuộc tính HV
                        dichVu = new DichVu(tendv1,giadv1);
                        //Add hv vào database
                        DataBaSe.getInstance(getActivity()).dao_dv().insertDV(dichVu);
                        //View list hv lên màn hình
                        loadData();
                        Log.d("zzz", "onViewCreated: " + list.size());
                        dialog.dismiss();

                    }
                });
                btnHuyAdddv.setOnClickListener(new View.OnClickListener() {
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
        list = (ArrayList<DichVu>) DataBaSe.getInstance(getActivity()).dao_dv().getAllDV();
        adapterListView_dichVu = new AdapterListView_DichVu(getActivity(),this::loadData);
        adapterListView_dichVu.setdata(list);
        lisCs.setAdapter(adapterListView_dichVu);
    }
}