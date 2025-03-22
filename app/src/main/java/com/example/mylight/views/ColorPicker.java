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
    private final int step = 1; //Step between each color sampled for the rainbow gradient.
    private int[] gradientColors = new int[360/step]; //list of sampled color
    private Paint piePaint; //Paint used to draw the wheel
    private Bitmap bitmap; //Bitmap used as an intermediary step. Needed to blend to shaders
    private float diameter; //Diameter of the color wheel
    private int height, width;
    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Create new paint
        piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //We want a whole disc, so we set it to fill
        piePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //Figure out how big we can make the pie.
        height = getHeight();
        width = getWidth();

        diameter = Math.min(width,height);
    }

    private void initBitmap(){
        //Create the bitmap
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        //Shader for the saturation gradient (a radial white gradient)
        Shader shaderA = new RadialGradient(getWidth()/2, getHeight()/2, diameter/2, Color.rgb(255,255,255), Color.rgb(0,0,0), CLAMP);

        //Shader for hue gradient (a rainbow sweep gradient)
        Shader shaderB = new SweepGradient(getWidth()/2, getHeight()/2, gradientColors, null);

        // Create a paint and apply first shader
        Paint sweepPaint = new Paint();
        sweepPaint.setShader(shaderB);

        //Draw the color wheel
        c.drawCircle(width/2, height/2, diameter/2, sweepPaint);

        //Create and apply second shader
        Paint gradPaint = new Paint();
        gradPaint.setShader(shaderA);

        //Enable shader blending, mode screen
        gradPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));

        //Add the gradient
        c.drawCircle(width/2, height/2, diameter/2, gradPaint);

        //Reset Xfermode
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

            //Sample color at given hue/angle
            gradientColors[(int)angle] = Color.HSVToColor(new float[]{angle,1,1});
            //set next angle
            angle += step;
        }

        //Draw on the bitmap first
        initBitmap();//This is needed because we can't blend shader directly in older API versions
        canvas.drawBitmap(bitmap, 0,0, null); //Draw on bitmap on screen
    }
}
