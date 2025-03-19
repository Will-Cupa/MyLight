package com.example.mylight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class ColorPicker extends View {
    final int[] COLORS2 = new int[]{Color.parseColor("#33004c"), Color.parseColor("#4600d2"),
            Color.parseColor("#0000ff"), Color.parseColor("#0099ff"),
            Color.parseColor("#00eeff"),Color.parseColor("#00FF7F"),
            Color.parseColor("#48FF00"),Color.parseColor("#B6FF00"),
            Color.parseColor("#FFD700"),Color.parseColor("#ff9500"),
            Color.parseColor("#FF6200"),Color.parseColor("#FF0000"),
            Color.parseColor("#33004c")};
    private Paint piePaint;
    private float diameter;

    private RectF bounds;
    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        piePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Account for padding.
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;

        // Figure out how big you can make the pie.
        diameter = Math.min(ww, hh);
        bounds = new RectF(0,0, getWidth(),getHeight());
        //piePaint.setShader(new SweepGradient(getWidth()/2, getHeight()/2, COLORS2, null));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float angle = 0;
        float step = 30;
        while(angle <360){
            piePaint.setColor(Color.rgb(255,255,0));
            canvas.drawArc(bounds,angle, step, true, piePaint);

            angle += step;
        }
        //canvas.drawCircle(getWidth()/2, getHeight()/2, diameter/2, piePaint);
    }
}
