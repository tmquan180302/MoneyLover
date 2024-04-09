package com.poly.moneylover.ui.transaction.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.poly.moneylover.R;
import com.poly.moneylover.utils.Convert;

import kotlin.jvm.internal.Intrinsics;

public final class PieChartMarkerView extends MarkerView {
    private final PieChart pieChart;
    private final TextView tvAmount;
    private final TextView tvPercent;
    private final TextView tvTitle;
    private final View vTriangleBottom;
    private final View vTriangleTop;

    public PieChartMarkerView(PieChart pieChart) {
        super(pieChart.getContext(), R.layout.layout_maker_pie_chart);
        Intrinsics.checkNotNullParameter(pieChart, "pieChart");
        this.pieChart = pieChart;
        View findViewById = getRootView().findViewById(R.id.tv_title);
        Intrinsics.checkNotNullExpressionValue(findViewById, "rootView.findViewById(R.id.tv_title)");
        this.tvTitle = (TextView) findViewById;
        View findViewById2 = getRootView().findViewById(R.id.tv_amount);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "rootView.findViewById(R.id.tv_amount)");
        this.tvAmount = (TextView) findViewById2;
        View findViewById3 = getRootView().findViewById(R.id.tv_percent);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "rootView.findViewById(R.id.tv_percent)");
        this.tvPercent = (TextView) findViewById3;
        View findViewById4 = getRootView().findViewById(R.id.v_triangle_bottom);
        Intrinsics.checkNotNullExpressionValue(findViewById4, "rootView.findViewById(R.id.v_triangle_bottom)");
        this.vTriangleBottom = findViewById4;
        View findViewById5 = getRootView().findViewById(R.id.v_triangle_top);
        Intrinsics.checkNotNullExpressionValue(findViewById5, "rootView.findViewById(R.id.v_triangle_top)");
        this.vTriangleTop = findViewById5;
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        PieEntry pieEntry = entry instanceof PieEntry ? (PieEntry) entry : null;
        if (pieEntry != null) {
            String pieLabel = this.pieChart.getData().getDataSets().get(0).getValueFormatter().getPieLabel((pieEntry.getY() / ((PieData) this.pieChart.getData()).getYValueSum()) * 100.0f, pieEntry);
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "context");
            this.tvTitle.setText(pieEntry.getLabel());
            this.tvAmount.setText(Convert.FormatNumber((long) pieEntry.getValue()) + "₫");
            this.tvPercent.setText(pieLabel + "%");
        }
        super.refreshContent(entry, highlight);
    }

    @Override
    public void draw(Canvas canvas, float f, float f2) {
        if (f2 < getHeight()) {
            this.vTriangleBottom.setVisibility(View.GONE);
            this.vTriangleTop.setVisibility(View.VISIBLE);
        } else {
            this.vTriangleBottom.setVisibility(View.VISIBLE);
            this.vTriangleTop.setVisibility(View.GONE);
            f2 -= getHeight();
        }
        super.draw(canvas, f - (getWidth() / 2), f2);
    }
}