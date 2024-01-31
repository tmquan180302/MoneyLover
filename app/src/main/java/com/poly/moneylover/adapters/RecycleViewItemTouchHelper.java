package com.poly.moneylover.adapters;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.interfaces.ItemHorizontalTouchHelper;

public class RecycleViewItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ItemHorizontalTouchHelper touchHelper;
    public RecycleViewItemTouchHelper(int dragDirs, int swipeDirs,ItemHorizontalTouchHelper helper) {
        super(dragDirs, swipeDirs);
        touchHelper = helper;
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ItemAdapterHorizontal.ItemViewHolder && viewHolder.getAdapterPosition() == 0) return 0;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(touchHelper != null){
            touchHelper.onSwide(viewHolder);
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            View foreGroundView = ((ItemAdapterHorizontal.ItemViewHolder) viewHolder).lnForeground;
            getDefaultUIUtil().onSelected(foreGroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foreGroundView = ((ItemAdapterHorizontal.ItemViewHolder) viewHolder).lnForeground;
        getDefaultUIUtil().onDraw(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder != null) {
            View foreGroundView = ((ItemAdapterHorizontal.ItemViewHolder) viewHolder).lnForeground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foreGroundView = ((ItemAdapterHorizontal.ItemViewHolder) viewHolder).lnForeground;
        getDefaultUIUtil().clearView(foreGroundView);
    }
}
