package com.example.mylight;

import static android.graphics.BlendMode.COLOR;
import static android.graphics.BlendMode.PLUS;
import static android.graphics.Shader.TileMode.CLAMP;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.compose.ui.graphics.TileMode;

import java.util.ArrayList;
import java.util.List;

public class ColorPicker extends View {

    private int step = 1;
    private int[] gradientColors = new int[360/step];
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // hsv[0] --> hue
        // hsv[1] --> saturation
        // hsv[2] --> value

        float angle = 0;
        while(angle <360){
            gradientColors[(int)angle] = Color.HSVToColor(new float[]{angle,1,1});
            angle += step;
        }

        Shader shader = new SweepGradient(getWidth()/2, getHeight()/2, gradientColors, null);
        piePaint.setShader(shader);

        canvas.drawCircle(getWidth()/2, getHeight()/2, diameter/2, piePaint);
    }
}
