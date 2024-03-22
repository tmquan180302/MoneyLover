package com.poly.moneylover.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.poly.moneylover.databinding.DatePickerDialogBinding;
import com.poly.moneylover.interfaces.ChangeMonthAndYearDialog;

import java.util.Calendar;

public class MonthYearPickerDialog extends DialogFragment {

    DatePickerDialogBinding datePickerDialogBinding;

    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1900;

    private ChangeMonthAndYearDialog listener;
    private Calendar calendar;
    private RadioButton rd_new_active;

    public MonthYearPickerDialog(ChangeMonthAndYearDialog listener, Calendar calendar) {
        this.listener = listener;
        this.calendar = calendar;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        datePickerDialogBinding = DatePickerDialogBinding.inflate(inflater);

        View dialog = datePickerDialogBinding.getRoot();

        switch (calendar.get(Calendar.MONTH) + 1) {
            case 1:
                datePickerDialogBinding.rd1.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd1;
                break;
            case 2:
                datePickerDialogBinding.rd2.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd2;
                break;
            case 3:
                datePickerDialogBinding.rd3.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd3;
                break;
            case 4:
                datePickerDialogBinding.rd4.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd4;
                break;
            case 5:
                datePickerDialogBinding.rd5.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd5;
                break;
            case 6:
                datePickerDialogBinding.rd6.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd6;
                break;
            case 7:
                datePickerDialogBinding.rd7.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd7;
                break;
            case 8:
                datePickerDialogBinding.rd8.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd8;
                break;
            case 9:
                datePickerDialogBinding.rd9.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd9;
                break;
            case 10:
                datePickerDialogBinding.rd10.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd10;
                break;
            case 11:
                datePickerDialogBinding.rd11.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd11;
                break;
            case 12:
                datePickerDialogBinding.rd12.setChecked(true);
                rd_new_active = datePickerDialogBinding.rd12;
                break;
        }

        datePickerDialogBinding.tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        datePickerDialogBinding.imvNext.setOnClickListener(v -> {
            if (calendar.get(Calendar.YEAR) > MIN_YEAR && calendar.get(Calendar.YEAR) < MAX_YEAR) {
                calendar.add(Calendar.YEAR, 1);
                datePickerDialogBinding.tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            } else {
                Toast.makeText(requireContext(), "Ngoài phạm vi!", Toast.LENGTH_SHORT).show();
            }
        });

        datePickerDialogBinding.imvBack.setOnClickListener(v -> {
            if (calendar.get(Calendar.YEAR) > MIN_YEAR && calendar.get(Calendar.YEAR) < MAX_YEAR) {
                calendar.add(Calendar.YEAR, -1);
                datePickerDialogBinding.tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            } else {
                Toast.makeText(requireContext(), "Ngoài phạm vi!", Toast.LENGTH_SHORT).show();
            }
        });

        datePickerDialogBinding.rdg1.setOnCheckedChangeListener((group, checkedId) -> {
            changeRadioButtonActive(dialog.findViewById(checkedId));
        });

        datePickerDialogBinding.rdg2.setOnCheckedChangeListener((group, checkedId) -> {
            changeRadioButtonActive(dialog.findViewById(checkedId));

        });

        datePickerDialogBinding.rdg3.setOnCheckedChangeListener((group, checkedId) -> {
            changeRadioButtonActive(dialog.findViewById(checkedId));

        });

        datePickerDialogBinding.rdg4.setOnCheckedChangeListener((group, checkedId) -> {
            changeRadioButtonActive(dialog.findViewById(checkedId));
        });

        datePickerDialogBinding.tvOk.setOnClickListener(v -> {
            listener.onClickOk(rd_new_active.getText().toString(),datePickerDialogBinding.tvYear.getText().toString());
            dismiss();
        });

        datePickerDialogBinding.tvCancel.setOnClickListener(v -> {
             dismiss();
        });

        builder.setView(dialog);

        return builder.create();
    }

    private void changeRadioButtonActive(RadioButton radioButtonNew) {
        rd_new_active.setChecked(false);
        rd_new_active = radioButtonNew;
    }

    interface ChangeMonthAndYearText {
        void onChangeMonthAndYear(String timeStampStart, String timeStampEnd);  // I lang nghe thay doi text (year) man rpFrg
    }
}