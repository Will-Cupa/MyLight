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

    private float centerX, centerY, radius;

    public ColorPickerFragment(){
        fragmentId = R.layout.fragment_color_picker;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View ColorWheel = view.findViewById(R.id.ColorWheel);

        centerX = ColorWheel.getX() + ColorWheel.getWidth()/2;
        centerY = ColorWheel.getY() + ColorWheel.getHeight()/2;


        ColorWheel.setOnTouchListener(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();

            float hue = (getAngleWithYAxis(x - v.getPivotX(), y - v.getPivotY()) + 360) % 360;

            int color = Color.HSVToColor(new float[]{hue,1,1});
            ((MainActivity)getActivity()).updateColor(color);
        }
        return true;
    }

    float getAngleWithYAxis(float x, float y) {
        return (float)Math.toDegrees(Math.atan2(y, x));
    }
}