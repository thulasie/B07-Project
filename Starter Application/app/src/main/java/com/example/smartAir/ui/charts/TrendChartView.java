package com.example.smartAir.ui.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TrendChartView extends View {

    private float[] data = new float[0];
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TrendChartView(Context context) {
        super(context);
        init();
    }

    public TrendChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint.setStrokeWidth(6f);
        linePaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeWidth(2f);
    }

    public void setData(int[] values) {
        if (values == null || values.length == 0) {
            data = new float[0];
            invalidate();
            return;
        }
        int max = 1;
        for (int v : values) if (v > max) max = v;
        data = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            data[i] = values[i] / (float) max;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        float w = getWidth(), h = getHeight(), pad = 16f;

        c.drawLine(pad, h - pad, w - pad, h - pad, axisPaint);

        if (data.length < 2) return;

        float stepX = (w - 2 * pad) / (data.length - 1);
        float prevX = pad;
        float prevY = h - pad - data[0] * (h - 2 * pad);

        for (int i = 1; i < data.length; i++) {
            float x = pad + i * stepX;
            float y = h - pad - data[i] * (h - 2 * pad);
            c.drawLine(prevX, prevY, x, y, linePaint);
            prevX = x;
            prevY = y;
        }
    }
}
