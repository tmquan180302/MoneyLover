package com.poly.moneylover.Fragment.nhanvien;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import thinhnh.fpoly.myapp.R;
import thinhnh.fpoly.myapp.adapter.AdapterListView_HoaDon;
import thinhnh.fpoly.myapp.csdl.DTO.HoaDon;
import thinhnh.fpoly.myapp.csdl.DTO.KhungGio;
import thinhnh.fpoly.myapp.csdl.DTO.TrangThaiHoaDon;
import thinhnh.fpoly.myapp.csdl.data.DataBaSe;
import thinhnh.fpoly.myapp.interfaces.InteLoadData;


public class TimKiemFragment extends Fragment {
    InteLoadData in;
ImageView imgngay,imgtimkiem;
int myear,mmonth,mday;
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy/MM/dd");
TextView tvngaythue;
ArrayList<HoaDon> listngay = new ArrayList<>();
AdapterListView_HoaDon adapterListView_hoaDon;
ListView list;
ArrayList<KhungGio> listkhunggioo = new ArrayList<>();
ArrayList<HoaDon> listkhungio = new ArrayList<>();
ArrayList<TrangThaiHoaDon> litstthd = new ArrayList<>();
Spinner spnkhungio,spntthd;
ImageView imgkhunggio;
ImageView imgtthd;
ArrayList<TrangThaiHoaDon> listtrangtthd = new ArrayList<>();
    public TimKiemFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TimKiemFragment newInstance() {
        TimKiemFragment fragment = new TimKiemFragment();

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
        return inflater.inflate(R.layout.fragment_tim_kiem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgngay = view.findViewById(R.id.imgngaytimkiem);
        imgtimkiem = view.findViewById(R.id.imgtimkiemngay);
        list = view.findViewById(R.id.lis_timkiemhoadon);
        tvngaythue = view.findViewById(R.id.tvngaytimkiem);
        spnkhungio = view.findViewById(R.id.spntimkiemkhunggio);
        spntthd  = view.findViewById(R.id.spntrangthaitimkiem);
        imgkhunggio = view.findViewById(R.id.imgtimkiemkhunggio);
        imgtthd = view.findViewById(R.id.timkiemtrangthai);
        imgtthd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hs2 = (HashMap<String, Object>)
                        spntthd.getSelectedItem();
                int makg11 = (int) hs2.get("matthd");
                String ten = (String) hs2.get("tentthd");
                listngay = (ArrayList<HoaDon>) DataBaSe.getInstance(getActivity()).dao_hoadon().getabctthd(ten);
                loadData();
            }
        });
        imgkhunggio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hs2 = (HashMap<String, Object>) spnkhungio.getSelectedItem();
                int makg1 = (int) hs2.get("makhunggio");
                listngay = (ArrayList<HoaDon>) DataBaSe.getInstance(getActivity()).dao_hoadon().gettimkiemkhunggio(makg1);
                loadData();
            }
        });

        SimpleAdapter simpleAdapter2 = new SimpleAdapter(getActivity(), getDSKhungGio(), android.R.layout.simple_list_item_1, new String[]{"khunggio"}, new int[]{android.R.id.text1});
        spnkhungio.setAdapter(simpleAdapter2);


        SimpleAdapter simpleAdapter4 = new SimpleAdapter(getActivity(), getDSTTHD(), android.R.layout.simple_list_item_1, new String[]{"tentthd"}, new int[]{android.R.id.text1});
        spntthd.setAdapter(simpleAdapter4);
        imgtimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "da click vao ", Toast.LENGTH_SHORT).show();
                String a = tvngaythue.getText().toString();
                listngay =(ArrayList<HoaDon>) DataBaSe.getInstance(getContext()).dao_hoadon().gettten(a);
                loadData();
            }
        });
        imgngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                myear = calendar.get(Calendar.YEAR);
                mmonth = calendar.get(Calendar.MONTH);
                mday = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),0,mdate,myear,mmonth,mday);
                dialog.show();
            }
        });
    }
    DatePickerDialog.OnDateSetListener mdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myear = year;
            mmonth = month;
            mday = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(myear,mmonth,mday);
            tvngaythue.setText(simpleDateFormat.format(calendar.getTime()));
        }
    };
    private void loadData() {
        //listhoadon = (ArrayList<HoaDon>) DataBaSe.getInstance(this).dao_hoadon().getAllHOADON();
        adapterListView_hoaDon = new AdapterListView_HoaDon(getActivity(), this::loadData);
        adapterListView_hoaDon.setdata(listngay);
        list.setAdapter(adapterListView_hoaDon);
    }
    private ArrayList<HashMap<String, Object>> getDSKhungGio() {

        listkhunggioo = (ArrayList<KhungGio>) DataBaSe.getInstance(getActivity()).dao_khunggio().getAllkhunggio();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (KhungGio khungGio : listkhunggioo) {
            HashMap<String, Object> hs2 = new HashMap<>();
            hs2.put("makhunggio", khungGio.getId_khunggio());
            hs2.put("khunggio",khungGio.getKhunggio());
            listHM.add(hs2);
        }
        return  listHM;

    }



    private ArrayList<HashMap<String, Object>> getDSTTHD() {

        litstthd = (ArrayList<TrangThaiHoaDon>) DataBaSe.getInstance(getActivity()).dao_tthd().getAllTTHD();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (TrangThaiHoaDon trangThaiHoaDon : litstthd) {
            HashMap<String, Object> hs4 = new HashMap<>();
            hs4.put("matthd", trangThaiHoaDon.getId_trangthaihd());
            hs4.put("tentthd",trangThaiHoaDon.getTentrangthai());

            listHM.add(hs4);
        }
        return  listHM;

    }

}