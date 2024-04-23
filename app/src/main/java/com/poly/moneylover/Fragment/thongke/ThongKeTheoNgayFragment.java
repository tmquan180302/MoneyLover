package com.poly.moneylover.Fragment.thongke;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import thinhnh.fpoly.myapp.R;
import thinhnh.fpoly.myapp.csdl.DTO.HoaDon;
import thinhnh.fpoly.myapp.csdl.data.DataBaSe;


public class ThongKeTheoNgayFragment extends Fragment {
//luu huu phuouc
    Button btntungay,btndenngay,btndoanhthu;
    EditText txttungay,txtdenngay;
    TextView tvdoanhthu;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    int myear,mmonth,mday;
    HoaDon hoaDon =new HoaDon();
    long tongThuDate = 0;
    List<HoaDon> listDoanhThu = new ArrayList<>();
    public ThongKeTheoNgayFragment() {
        // Required empty public constructor
    }


    public static ThongKeTheoNgayFragment newInstance() {
        ThongKeTheoNgayFragment fragment = new ThongKeTheoNgayFragment();

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
        return inflater.inflate(R.layout.fragment_thong_ke__doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btntungay = view.findViewById(R.id.btntungay);
        btndenngay = view.findViewById(R.id.btndenngay);
        btndoanhthu = view.findViewById(R.id.btnDoanhThu);
        txttungay = view.findViewById(R.id.edt_ngayBatDau);
        txtdenngay = view.findViewById(R.id.ngayketthuc);
        tvdoanhthu = view.findViewById(R.id.tv_doanhthu);
        btntungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                myear = calendar.get(Calendar.YEAR);
                mmonth= calendar.get(Calendar.MONTH);
                mday = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),0,mdatetungay,myear,mmonth,mday);
                dialog.show();
            }
        });
        btndenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                myear = calendar.get(Calendar.YEAR);
                mmonth= calendar.get(Calendar.MONTH);
                mday = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),0,mdatedenngay,myear,mmonth,mday);
                dialog.show();
            }
        });

        btndoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tongThuDate = 0;
                    //tính tiền theo ngày
                    listDoanhThu = DataBaSe.getInstance(getActivity()).dao_hoadon().getDoanhThu(txttungay.getText().toString(), txtdenngay.getText().toString());
                    if(listDoanhThu.size() == 0){

                        tvdoanhthu.setText(String.valueOf(tongThuDate));
                    }
                    else {
                        for (int i = 0; i < listDoanhThu.size(); i++) {

                                tongThuDate = tongThuDate + listDoanhThu.get(i).getTongtien();

                        }
                        if (String.valueOf(tongThuDate).length() > 9){
                            Toast.makeText(getActivity(), String.valueOf(tongThuDate).length()+"" , Toast.LENGTH_SHORT).show();
                            tongThuDate = (long) (Math.floor((tongThuDate / 1000000000)*100)/100);
                            tvdoanhthu.setText(tongThuDate+"");
                        }else if (String.valueOf(tongThuDate).length() > 6){
                            tongThuDate = (long) (Math.floor((tongThuDate / 1000000)*100)/100);
                            tvdoanhthu.setText(tongThuDate+"");
                        }else{
                            tvdoanhthu.setText(tongThuDate+"");
                        }

                }

//                    Log.d("zzz", "onClick: " + tongThuDate);
            }
        });

    }
    DatePickerDialog.OnDateSetListener mdatetungay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            myear = i;
            mmonth = i1;
            mday = i2;
            GregorianCalendar c = new GregorianCalendar(myear,mmonth,mday);
            txttungay.setText(simpleDateFormat.format(c.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mdatedenngay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            myear = i;
            mmonth = i1;
            mday = i2;
            GregorianCalendar c = new GregorianCalendar(myear,mmonth,mday);
            txtdenngay.setText(simpleDateFormat.format(c.getTime()));
        }
    };
    }
