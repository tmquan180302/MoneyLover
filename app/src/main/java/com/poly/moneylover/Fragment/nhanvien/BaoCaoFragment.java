package com.poly.moneylover.Fragment.nhanvien;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import thinhnh.fpoly.myapp.R;
import thinhnh.fpoly.myapp.adapter.AdapterListView_BaoCao;
import thinhnh.fpoly.myapp.csdl.DTO.BaoCao;
import thinhnh.fpoly.myapp.csdl.DTO.San;
import thinhnh.fpoly.myapp.csdl.data.DataBaSe;

public class BaoCaoFragment extends Fragment {
     TextView sonv;
     ListView lisCs;
     FloatingActionButton floatCs;
AdapterListView_BaoCao adapterListView_san;
    ArrayList<San> listsan = new ArrayList<>();
     Spinner spntensan;
     EditText edittextmota;


     Button btnAddSan;
     Button btnHuyAddSan;

    ArrayList<BaoCao> list1 = new ArrayList<>();
 BaoCao baoCao;



    public BaoCaoFragment() {
        // Required empty public constructor
    }


    public static BaoCaoFragment newInstance() {
        BaoCaoFragment fragment = new BaoCaoFragment();

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
        return inflater.inflate(R.layout.fragment_bao_cao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        sonv = (TextView) view.findViewById(R.id.sonv);
        lisCs = (ListView) view.findViewById(R.id.lis_cs);
        floatCs = (FloatingActionButton) view.findViewById(R.id.float_cs);
        loadData();
        floatCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_baocao);


                spntensan = (Spinner) dialog.findViewById(R.id.spntensan);
                edittextmota = (EditText) dialog.findViewById(R.id.edittextmota);


                btnAddSan = (Button) dialog.findViewById(R.id.btnAddSan);
                btnHuyAddSan = (Button) dialog.findViewById(R.id.btnHuyAddSan);
                SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), getDSSan(), android.R.layout.simple_list_item_1, new String[]{"tensan"}, new int[]{android.R.id.text1});
                spntensan.setAdapter(simpleAdapter);
                btnAddSan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (validate()){}

                        HashMap<String,Object> hs1 = (HashMap<String, Object>) spntensan.getSelectedItem();
                        int masan = (int) hs1.get("masan");

                        String tensan = (String) hs1.get("tensan");
                        String giasan = (String) hs1.get("giasan");

                        String mota = edittextmota.getText().toString();

                        //set thuộc tính HV
                        baoCao  = new BaoCao(masan,tensan,giasan,mota);
                        //Add hv vào database
                        DataBaSe.getInstance(getActivity()).dao_baoCao().insertBC(baoCao);
                        //View list hv lên màn hình
                        loadData();
                        Log.d("zzz", "onViewCreated: " + list1.size());
                        dialog.dismiss();

                    }
                });
                btnHuyAddSan.setOnClickListener(new View.OnClickListener() {
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
        list1 = (ArrayList<BaoCao>) DataBaSe.getInstance(getActivity()).dao_baoCao().getAllBc();
        adapterListView_san = new AdapterListView_BaoCao(getActivity(),this::loadData);
        adapterListView_san.setdata(list1);
        lisCs.setAdapter(adapterListView_san);



    }
    private ArrayList<HashMap<String, Object>> getDSSan() {

        listsan = (ArrayList<San>) DataBaSe.getInstance(getActivity()).dao_san().getAllSan();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (San san : listsan) {
            HashMap<String, Object> hs1 = new HashMap<>();
            hs1.put("masan", san.getId_san());
            hs1.put("tensan", san.getTensan());
            hs1.put("giasan", san.getGiasan());
            listHM.add(hs1);
        }
        return  listHM;

    }

}