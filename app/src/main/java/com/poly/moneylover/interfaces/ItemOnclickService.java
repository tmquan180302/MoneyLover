package com.poly.moneylover.interfaces;

import android.widget.LinearLayout;

import com.poly.moneylover.models.ReportModels.ReportCategoryDTO;
import com.poly.moneylover.models.Service;

public interface ItemOnclickService {
    void onSelectedService(Service service);

    void onChangeBg(Service service, LinearLayout linearLayout);

}
