package com.poly.moneylover.utils;

import android.app.Activity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class EditTextUtils {
    public static void ListenUnfocus(EditText editText){
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Device.hideKeyBoard((Activity) editText.getContext());
                editText.clearFocus();
                return true;
            }
            return false;
        });
    }

}
