package com.example.mylight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ColorPicker extends View {
    private Paint piePaint;
    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        piePaint.setStyle(Paint.Style.FILL);
        piePaint.setColor(Color.rgb(255,255,0));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(0,0,100, piePaint);
    }
}
