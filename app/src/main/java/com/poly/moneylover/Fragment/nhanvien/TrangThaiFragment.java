package com.poly.moneylover.Fragment.nhanvien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import thinhnh.fpoly.myapp.R;

public class TrangThaiFragment extends Fragment {



    public TrangThaiFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TrangThaiFragment newInstance() {
        TrangThaiFragment fragment = new TrangThaiFragment();

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
        return inflater.inflate(R.layout.fragment_trang_thai, container, false);
    }
}