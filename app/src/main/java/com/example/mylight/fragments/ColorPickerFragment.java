package com.example.mylight.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.mylight.MainActivity;
import com.example.mylight.R;

public class ColorPickerFragment extends ColorSelectFragment  implements View.OnTouchListener {

    private float radius;

    public ColorPickerFragment(){
        fragmentId = R.layout.fragment_color_picker;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View ColorWheel = view.findViewById(R.id.ColorWheel);

        radius = 0.0f;

        ColorWheel.setOnTouchListener(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if(radius == 0.0f){
                radius = Math.min(v.getWidth(),v.getHeight());
            }
            float x = event.getX() - v.getPivotX();
            float y = event.getY() - v.getPivotY();

            float saturation = (float)Math.sqrt(x*x + y*y)/ radius;
            float hue = (getAngleWithYAxis(x,y) + 360) % 360;

            int color = Color.HSVToColor(new float[]{hue,saturation,1});
            ((MainActivity)getActivity()).updateColor(color);
        }
        return true;
    }

    float getAngleWithYAxis(float x, float y) {
        return (float)Math.toDegrees(Math.atan2(y, x));
    }
}