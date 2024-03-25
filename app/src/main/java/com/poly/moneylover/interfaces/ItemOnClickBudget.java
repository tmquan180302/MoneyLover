package com.poly.moneylover.interfaces;

import com.poly.moneylover.models.Budget;

public interface ItemOnClickBudget {
    void delete(String id);

    void onSelectdBudgetCategory(Budget budget);
}
