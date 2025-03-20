package com.example.mylight.views;

import static android.graphics.BlendMode.COLOR;
import static android.graphics.BlendMode.PLUS;
import static android.graphics.Shader.TileMode.CLAMP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    private Bitmap bitmap;
    private float diameter;
    private int height, width;
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
        height = getHeight();
        width = getWidth();
        diameter = Math.min(ww, hh);
    }

    private void initBitmap(){
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        Shader shaderA = new RadialGradient(getWidth()/2, getHeight()/2, diameter/2, Color.rgb(255,255,255), Color.rgb(0,0,0), CLAMP);
        Shader shaderB = new SweepGradient(getWidth()/2, getHeight()/2, gradientColors, null);

        // Create a paint and apply first shader
        Paint sweepPaint = new Paint();
        sweepPaint.setShader(shaderB);

        c.drawCircle(width/2, height/2, diameter/2, sweepPaint);

        //Create and apply second shader
        Paint gradPaint = new Paint();
        gradPaint.setShader(shaderA);

        gradPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN)); //enable shader blending

        c.drawCircle(width/2, height/2, diameter/2, gradPaint);

        // Reset Xfermode
        gradPaint.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float angle = 0;
        while(angle <360){
            // hsv[0] --> hue
            // hsv[1] --> saturation
            // hsv[2] --> value
            gradientColors[(int)angle] = Color.HSVToColor(new float[]{angle,1,1});
            angle += step;
        }

        initBitmap();
        canvas.drawBitmap(bitmap, 0,0, null);
    }
}
