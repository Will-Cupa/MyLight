package com.example.mylight.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.example.mylight.MainActivity;
import com.example.mylight.R;

public class ColorPickerFragment extends ColorSelectFragment  implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

    private float hue, saturation, value, radius;

    public ColorPickerFragment(){
        fragmentId = R.layout.fragment_color_picker;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View colorWheel = view.findViewById(R.id.ColorWheel);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.BrightnessSlider);

        radius = 0;
        hue = 0;
        saturation = 1;
        value = 1;

        seekBar.setOnSeekBarChangeListener(this);
        colorWheel.setOnTouchListener(this);
    }

    public void updateColor(){
        int color = Color.HSVToColor(new float[]{hue,saturation,value});
        ((MainActivity)getActivity()).updateColor(color);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if(radius == 0.0f){
                radius = Math.min(v.getWidth(),v.getHeight());
            }
            float x = event.getX() - v.getPivotX();
            float y = event.getY() - v.getPivotY();

            saturation = (float)Math.sqrt(x*x + y*y)/ radius;
            hue = (getAngleWithYAxis(x,y) + 360) % 360;

            updateColor();
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean formUser) {
        value = progress/1000.0f;
        updateColor();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Log.d("slider", String.valueOf(seekBar.getProgress()));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Log.d("slider", String.valueOf(seekBar.getProgress()));
    }

    float getAngleWithYAxis(float x, float y) {
        return (float)Math.toDegrees(Math.atan2(y, x));
    }
}