package com.poly.moneylover.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.poly.moneylover.EditActivity;
import com.poly.moneylover.R;
import com.poly.moneylover.adapters.ItemAdapter;
import com.poly.moneylover.interfaces.ItemOnclick;
import com.poly.moneylover.models.Dto_item;
import com.poly.moneylover.models.Item;
import com.poly.moneylover.ui.CalendarFragment;
import com.poly.moneylover.ui.InputFragment;

public class DetailitemlichActivity extends AppCompatActivity implements ItemOnclick {
    private ImageButton back;
    private ImageButton edit;
    private ImageButton imbReduceDay;
    private TextView tvSelectedDate;
    private ImageButton imbIncreaseDay;
    private EditText edtNote;
    private EditText edtMoney;
    private RecyclerView rcvItem;
    private Button btnInput;
    private int INDEX = 0;
    private ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitemlich);
anhxa();
getdata();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();


            }
        });

    }
    private void anhxa(){
        back = (ImageButton) findViewById(R.id.back);
        edit = (ImageButton) findViewById(R.id.edit);
        imbReduceDay = (ImageButton) findViewById(R.id.imb_reduce_day);
        tvSelectedDate = (TextView) findViewById(R.id.tv_selected_date);
        imbIncreaseDay = (ImageButton) findViewById(R.id.imb_increase_day);
        edtNote = (EditText) findViewById(R.id.edt_note);
        edtMoney = (EditText) findViewById(R.id.edt_money);
        rcvItem = (RecyclerView) findViewById(R.id.rcv_item);
        btnInput = (Button) findViewById(R.id.btn_input);


        itemAdapter = new ItemAdapter(this);
        rcvItem.setAdapter(itemAdapter);
        itemAdapter.setList(Item.getListItemTienChi());
    }
    private  void getdata(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Dto_item selectedItem = (Dto_item) bundle.getSerializable("obj");

            tvSelectedDate.setText(selectedItem.getNgay());
            edtNote.setText(selectedItem.getGhichu());
            edtMoney.setText(selectedItem.getTien());
            itemAdapter.setPositionSelected(selectedItem.getDanhmuc());



        }
    }


    @Override
    public void getIdItemSelected(int itemId) {

    }

    @Override
    public void editItem() {

    }
}