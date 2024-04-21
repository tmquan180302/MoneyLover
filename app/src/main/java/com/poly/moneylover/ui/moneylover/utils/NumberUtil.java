package com.poly.moneylover.ui.moneylover.utils;

import java.text.DecimalFormat;

public class NumberUtil {

    // Format number 600000 ==> 600.000
    public static String formatNumberCurrent(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(Double.parseDouble(number));
    }
}
