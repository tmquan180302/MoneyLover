package com.poly.moneylover.ui.transaction.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;

import androidx.core.view.ViewCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public final class CustomPieChartRenderer extends PieChartRenderer {
    private final ArrayList<RectF> arrBoundDrawLabel;
    private final Context context;
    private final TextPaint textPaint;

    public CustomPieChartRenderer(PieChart pieChart) {
        super(pieChart, pieChart.getAnimator(), pieChart.getViewPortHandler());
        Intrinsics.checkNotNullParameter(pieChart, "pieChart");
        this.arrBoundDrawLabel = new ArrayList<>();
        this.context = pieChart.getContext();
        TextPaint textPaint = new TextPaint(1);
        this.textPaint = textPaint;
        textPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(com.github.mikephil.charting.utils.Utils.convertDpToPixel(13.0f));
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void drawValues(Canvas canvas) {
        List<IPieDataSet> list;
        int i;
        int i2;
        float f;
        float[] fArr;
        float[] fArr2;
        float f2;
        float f3;
        float f4;
        Canvas canvas2;
        int i3;
        float[] fArr3;
        float[] fArr4;
        float f5;
        ValueFormatter valueFormatter;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        String formattedValue;
        float f12;
        boolean z;
        Object obj;
        MPPointF mPPointF;
        int i4;
        PieEntry pieEntry;
        float f13;
        PieDataSet.ValuePosition valuePosition;
        float f14;
        IPieDataSet iPieDataSet;
        Canvas canvas3;
        PieDataSet.ValuePosition valuePosition2;
        int i5;
        MPPointF mPPointF2;
        Canvas canvas4 = canvas;
        TextPaint textPaint = this.textPaint;
        Context context = this.context;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        textPaint.setColor(Color.BLACK);
        this.arrBoundDrawLabel.clear();
        MPPointF centerCircleBox = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        float rotationAngle = this.mChart.getRotationAngle();
        float[] drawAngles = this.mChart.getDrawAngles();
        float[] absoluteAngles = this.mChart.getAbsoluteAngles();
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float holeRadius = (radius - ((this.mChart.getHoleRadius() * radius) / 100.0f)) / 2.0f;
        float holeRadius2 = this.mChart.getHoleRadius() / 100.0f;
        float f15 = (radius / 10.0f) * 3.6f;
        if (this.mChart.isDrawHoleEnabled()) {
            f15 = (radius - (radius * holeRadius2)) / 2.0f;
            if (!this.mChart.isDrawSlicesUnderHoleEnabled() && this.mChart.isDrawRoundedSlicesEnabled()) {
                rotationAngle += (float) ((holeRadius * 360) / (radius * 6.283185307179586d));
            }
        }
        float f16 = rotationAngle;
        float f17 = radius - f15;
        PieData pieData = (PieData) this.mChart.getData();
        List<IPieDataSet> dataSets = pieData.getDataSets();
        float yValueSum = pieData.getYValueSum();
        boolean isDrawEntryLabelsEnabled = this.mChart.isDrawEntryLabelsEnabled();
        Intrinsics.checkNotNull(canvas);
        canvas.save();
        float convertDpToPixel = com.github.mikephil.charting.utils.Utils.convertDpToPixel(5.0f);
        int size = dataSets.size();
        int i6 = 0;
        boolean z2 = false;
        int i7 = 0;
        while (i7 < size) {
            IPieDataSet iPieDataSet2 = dataSets.get(i7);
            boolean isDrawValuesEnabled = iPieDataSet2.isDrawValuesEnabled();
            if (isDrawValuesEnabled || isDrawEntryLabelsEnabled) {
                PieDataSet.ValuePosition xValuePosition = iPieDataSet2.getXValuePosition();
                list = dataSets;
                PieDataSet.ValuePosition yValuePosition = iPieDataSet2.getYValuePosition();
                int i8 = i6;
                applyValueTextStyle(iPieDataSet2);
                boolean z3 = z2;
                float calcTextHeight = com.github.mikephil.charting.utils.Utils.calcTextHeight(this.mValuePaint, "Q") + com.github.mikephil.charting.utils.Utils.convertDpToPixel(4.0f);
                ValueFormatter valueFormatter2 = iPieDataSet2.getValueFormatter();
                int entryCount = iPieDataSet2.getEntryCount();
                i = i7;
                i2 = size;
                this.mValueLinePaint.setColor(iPieDataSet2.getValueLineColor());
                this.mValueLinePaint.setStrokeWidth(com.github.mikephil.charting.utils.Utils.convertDpToPixel(iPieDataSet2.getValueLineWidth()));
                float sliceSpace = getSliceSpace(iPieDataSet2);
                MPPointF mPPointF3 = MPPointF.getInstance(iPieDataSet2.getIconsOffset());
                mPPointF3.x = com.github.mikephil.charting.utils.Utils.convertDpToPixel(mPPointF3.x);
                mPPointF3.y = com.github.mikephil.charting.utils.Utils.convertDpToPixel(mPPointF3.y);
                int i9 = 0;
                while (i9 < entryCount) {
                    MPPointF mPPointF4 = mPPointF3;
                    PieEntry entryForIndex = iPieDataSet2.getEntryForIndex(i9);
                    if (entryForIndex == null) {
                        i3 = entryCount;
                        valueFormatter = valueFormatter2;
                        valuePosition = yValuePosition;
                        valuePosition2 = xValuePosition;
                        f11 = radius;
                        fArr3 = drawAngles;
                        fArr4 = absoluteAngles;
                        f5 = phaseX;
                        f6 = phaseY;
                        f7 = f16;
                        mPPointF2 = mPPointF4;
                        iPieDataSet = iPieDataSet2;
                        i4 = i9;
                    } else {
                        i3 = entryCount;
                        float f18 = f16 + (((i8 == 0 ? 0.0f : absoluteAngles[i8 - 1] * phaseX) + ((drawAngles[i8] - ((sliceSpace / (f17 * 0.017453292f)) / 2.0f)) / 2.0f)) * phaseY);
                        fArr3 = drawAngles;
                        float y = this.mChart.isUsePercentValuesEnabled() ? (entryForIndex.getY() / yValueSum) * 100.0f : entryForIndex.getY();
                        fArr4 = absoluteAngles;
                        String pieLabel = valueFormatter2.getPieLabel(y, entryForIndex);
                        f5 = phaseX;
                        String entryLabel = entryForIndex.getLabel();
                        valueFormatter = valueFormatter2;
                        f6 = phaseY;
                        f7 = f16;
                        double d = f18 * 0.017453292f;
                        int i10 = i9;
                        float cos = (float) Math.cos(d);
                        float f19 = y;
                        float sin = (float) Math.sin(d);
                        boolean z4 = isDrawEntryLabelsEnabled && xValuePosition == PieDataSet.ValuePosition.OUTSIDE_SLICE;
                        boolean z5 = isDrawValuesEnabled && yValuePosition == PieDataSet.ValuePosition.OUTSIDE_SLICE;
                        boolean z6 = isDrawEntryLabelsEnabled && xValuePosition == PieDataSet.ValuePosition.INSIDE_SLICE;
                        boolean z7 = isDrawValuesEnabled && yValuePosition == PieDataSet.ValuePosition.INSIDE_SLICE;
                        if (z4 || z5) {
                            float valueLinePart1Length = iPieDataSet2.getValueLinePart1Length();
                            float valueLinePart2Length = iPieDataSet2.getValueLinePart2Length();
                            float valueLinePart1OffsetPercentage = iPieDataSet2.getValueLinePart1OffsetPercentage() / 100.0f;
                            PieDataSet.ValuePosition valuePosition3 = yValuePosition;
                            if (this.mChart.isDrawHoleEnabled()) {
                                float f20 = radius * holeRadius2;
                                f8 = ((radius - f20) * valueLinePart1OffsetPercentage) + f20;
                            } else {
                                f8 = radius * valueLinePart1OffsetPercentage;
                            }
                            float abs = iPieDataSet2.isValueLineVariableLength() ? valueLinePart2Length * f17 * ((float) Math.abs(Math.sin(d))) : valueLinePart2Length * f17;
                            float f21 = (f8 * cos) + centerCircleBox.x;
                            float f22 = (f8 * sin) + centerCircleBox.y;
                            PieDataSet.ValuePosition valuePosition4 = xValuePosition;
                            float f23 = (1 + valueLinePart1Length) * f17;
                            float f24 = centerCircleBox.x + (f23 * cos);
                            float f25 = (f23 * sin) + centerCircleBox.y;
                            double d2 = f18 % 360.0d;
                            if (90.0d <= d2 && d2 <= 270.0d) {
                                float f26 = f24 - abs;
                                this.mValuePaint.setTextAlign(Paint.Align.RIGHT);
                                if (z4) {
                                    this.textPaint.setTextAlign(Paint.Align.RIGHT);
                                }
                                f9 = f26 - convertDpToPixel;
                                f10 = f26;
                            } else {
                                float f27 = f24 + abs;
                                this.mValuePaint.setTextAlign(Paint.Align.LEFT);
                                if (z4) {
                                    this.textPaint.setTextAlign(Paint.Align.LEFT);
                                }
                                f9 = f27 + convertDpToPixel;
                                f10 = f27;
                            }
                            PointF pointF = new PointF(f10, f25);
                            float f28 = f9;
                            Intrinsics.checkNotNullExpressionValue(entryLabel, "entryLabel");
                            f11 = radius;
                            formattedValue = pieLabel;
                            Intrinsics.checkNotNullExpressionValue(formattedValue, "formattedValue");
                            float maxWidthLabelSplice = getMaxWidthLabelSplice(entryLabel, formattedValue);
                            if (f21 < f10) {
                                f12 = f10;
                                z = true;
                            } else {
                                f12 = f10;
                                z = false;
                            }
                            RectF boundsDrawText = getBoundsDrawText(pointF, z, maxWidthLabelSplice);
                            Iterator it = this.arrBoundDrawLabel.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    obj = null;
                                    break;
                                }
                                obj = it.next();
                                Iterator it2 = it;
                                if (((RectF) obj).intersect(boundsDrawText)) {
                                    break;
                                }
                                it = it2;
                            }
                            boolean z8 = obj != null || f19 < 5.0f;
                            if (iPieDataSet2.getValueLineColor() == 1122867 || z8) {
                                mPPointF = mPPointF4;
                                i4 = i10;
                                pieEntry = entryForIndex;
                                f13 = cos;
                                valuePosition = valuePosition3;
                                f14 = sin;
                                iPieDataSet = iPieDataSet2;
                            } else {
                                this.arrBoundDrawLabel.add(boundsDrawText);
                                if (iPieDataSet2.isUsingSliceColorAsValueLineColor()) {
                                    i5 = i10;
                                    this.mValueLinePaint.setColor(iPieDataSet2.getColor(i5));
                                } else {
                                    i5 = i10;
                                }
                                i4 = i5;
                                mPPointF = mPPointF4;
                                pieEntry = entryForIndex;
                                f13 = cos;
                                valuePosition = valuePosition3;
                                f14 = sin;
                                iPieDataSet = iPieDataSet2;
                                canvas.drawLine(f21, f22, f24, f25, this.mValueLinePaint);
                                canvas.drawLine(f24, f25, f12, f25, this.mValueLinePaint);
                            }
                            if (z4 && z5 && !z8) {
                                drawValue(canvas, formattedValue, f28, f25 - 20, iPieDataSet.getValueTextColor(i4));
                                if (i4 < pieData.getEntryCount()) {
                                    canvas3 = canvas;
                                    valuePosition2 = valuePosition4;
                                    drawEntryLabel(canvas3, entryLabel, f28, f25 - 20);
                                } else {
                                    canvas3 = canvas;
                                    valuePosition2 = valuePosition4;
                                }
                            } else {
                                canvas3 = canvas;
                                valuePosition2 = valuePosition4;
                                if (!z4 || z8) {
                                    if (z5 && !z8) {
                                        drawValue(canvas, formattedValue, f28, f25 + (calcTextHeight / 2.0f), iPieDataSet.getValueTextColor(i4));
                                    }
                                } else if (i4 < pieData.getEntryCount()) {
                                    drawEntryLabel(canvas3, entryLabel, f28, f25 + (calcTextHeight / 2.0f));
                                }
                            }
                            z3 = z8;
                        } else {
                            canvas3 = canvas;
                            valuePosition = yValuePosition;
                            f11 = radius;
                            f14 = sin;
                            mPPointF = mPPointF4;
                            i4 = i10;
                            pieEntry = entryForIndex;
                            formattedValue = pieLabel;
                            iPieDataSet = iPieDataSet2;
                            f13 = cos;
                            valuePosition2 = xValuePosition;
                        }
                        if (z6 || z7) {
                            float f29 = (f17 * f13) + centerCircleBox.x;
                            float f30 = (f17 * f14) + centerCircleBox.y;
                            this.mValuePaint.setTextAlign(Paint.Align.CENTER);
                            if (z6 && z7 && !z3) {
                                drawValue(canvas, formattedValue, f29, f30, iPieDataSet.getValueTextColor(i4));
                                if (i4 < pieData.getEntryCount() && entryLabel != null) {
                                    drawEntryLabel(canvas3, entryLabel, f29, f30 + calcTextHeight);
                                }
                            } else {
                                if (!z6 || z3) {
                                    if (!z3) {
                                        drawValue(canvas, formattedValue, f29, f30 + (calcTextHeight / 2.0f), iPieDataSet.getValueTextColor(i4));
                                    }
                                } else if (i4 < pieData.getEntryCount() && entryLabel != null) {
                                    drawEntryLabel(canvas3, entryLabel, f29, f30 + (calcTextHeight / 2.0f));
                                }
                                if (pieEntry.getIcon() == null && iPieDataSet.isDrawIconsEnabled()) {
                                    Drawable icon = pieEntry.getIcon();
                                    mPPointF2 = mPPointF;
                                    com.github.mikephil.charting.utils.Utils.drawImage(canvas, icon, (int) (((mPPointF2.y + f17) * f13) + centerCircleBox.x), (int) (((mPPointF2.y + f17) * f14) + centerCircleBox.y + mPPointF2.x), icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                                } else {
                                    mPPointF2 = mPPointF;
                                }
                                i8++;
                            }
                        }
                        if (pieEntry.getIcon() == null) {
                        }
                        mPPointF2 = mPPointF;
                        i8++;
                    }
                    i9 = i4 + 1;
                    mPPointF3 = mPPointF2;
                    iPieDataSet2 = iPieDataSet;
                    drawAngles = fArr3;
                    entryCount = i3;
                    phaseY = f6;
                    absoluteAngles = fArr4;
                    phaseX = f5;
                    valueFormatter2 = valueFormatter;
                    f16 = f7;
                    xValuePosition = valuePosition2;
                    yValuePosition = valuePosition;
                    radius = f11;
                }
                f = radius;
                fArr = drawAngles;
                fArr2 = absoluteAngles;
                f2 = phaseX;
                f3 = phaseY;
                f4 = f16;
                canvas2 = canvas;
                MPPointF.recycleInstance(mPPointF3);
                i6 = i8;
                z2 = z3;
            } else {
                i = i7;
                i2 = size;
                list = dataSets;
                f = radius;
                fArr = drawAngles;
                fArr2 = absoluteAngles;
                f2 = phaseX;
                f3 = phaseY;
                f4 = f16;
                canvas2 = canvas4;
            }
            i7 = i + 1;
            canvas4 = canvas2;
            dataSets = list;
            size = i2;
            drawAngles = fArr;
            phaseY = f3;
            absoluteAngles = fArr2;
            phaseX = f2;
            f16 = f4;
            radius = f;
        }
        MPPointF.recycleInstance(centerCircleBox);
        canvas.restore();
    }

    @Override
    public void drawHole(Canvas canvas) {
        TypedValue typedValue = new TypedValue();
        this.context.getTheme().resolveAttribute(Color.WHITE, typedValue, true);
        this.mHolePaint.setColor(typedValue.data);
        super.drawHole(canvas);
    }

    @Override
    protected void drawEntryLabel(Canvas canvas, String str, float x, float y) {
        StringBuilder stringBuilder = new StringBuilder();
        TextPaint textPaint = this.textPaint;

        float maxWidth = 110;
        float lineHeight = textPaint.getTextSize();

        String[] words = str.split("\\s+");

        for (String word : words) {
            float wordWidth = textPaint.measureText(word + " ");

            if (stringBuilder.length() > 0 && x + wordWidth > maxWidth) {
                // Vẽ dòng hiện tại
                StaticLayout staticLayout = new StaticLayout(stringBuilder.toString().trim(), textPaint,
                        (int) Math.ceil(maxWidth), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

                canvas.save();
                canvas.translate(x, y);

                staticLayout.draw(canvas);

                canvas.restore();

                y += lineHeight;
                stringBuilder = new StringBuilder();
                stringBuilder.append(word).append(" ");
            } else {
                stringBuilder.append(word).append(" ");
            }
        }

        if (stringBuilder.length() > 0) {
            StaticLayout staticLayout = new StaticLayout(stringBuilder.toString().trim(), textPaint,
                    (int) Math.ceil(maxWidth), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

            canvas.save();
            canvas.translate(x, y);

            staticLayout.draw(canvas);

            canvas.restore();
        }
    }



    @Override
    public void drawValue(Canvas canvas, String str, float f, float f2, int i) {
        super.drawValue(canvas, str, f, f2, this.textPaint.getColor());
    }

    private final RectF getBoundsDrawText(PointF pointF, boolean z, float f) {
        float f2;
        float f3;
        float convertDpToPixel = com.github.mikephil.charting.utils.Utils.convertDpToPixel(5.0f);
        float textSize = (pointF.y - this.mValueLinePaint.getTextSize()) - convertDpToPixel;
        if (z) {
            f2 = pointF.x;
            f3 = pointF.x + f;
        } else {
            f2 = pointF.x - f;
            f3 = pointF.x;
        }
        return new RectF(f2, textSize, f3, pointF.y + this.textPaint.getTextSize() + (convertDpToPixel / 2));
    }

    private final float getMaxWidthLabelSplice(String str, String str2) {
        Rect rect = new Rect();
        this.textPaint.getTextBounds(str, 0, str.length(), rect);
        float width = rect.width() + 1.0f;
        this.textPaint.getTextBounds(str2, 0, str2.length(), rect);
        return ((float) rect.width()) > width ? rect.width() * 1.0f : width;
    }
}