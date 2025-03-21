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

    private float radius, colorHSV[];

    public ColorPickerFragment(){
        fragmentId = R.layout.fragment_color_picker;
        radius = 0;
        colorHSV = new float[3];
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get view components
        View colorWheel = view.findViewById(R.id.ColorWheel);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.BrightnessSlider);

        //Get color from main activity
        int color = ((MainActivity)getActivity()).getLampColor();
        //Convert it to HSV
        Color.colorToHSV(color, colorHSV);

        //Update slider position based on "value" field in HSV
        seekBar.setProgress((int)(colorHSV[2]*1000));

        //Add listener to component
        seekBar.setOnSeekBarChangeListener(this);
        colorWheel.setOnTouchListener(this);
    }

    public void updateColor(){
        int color = Color.HSVToColor(colorHSV);

        //Set color in main activity
        ((MainActivity)getActivity()).updateColor(color);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if(radius == 0.0f){
                radius = Math.min(v.getWidth(),v.getHeight());
            }

            //Center of circle
            float x = event.getX() - v.getPivotX();
            float y = event.getY() - v.getPivotY();

            colorHSV[1] = (float)Math.sqrt(x*x + y*y)/ radius; //Saturation
            colorHSV[0] = (getAngleWithYAxis(x,y) + 360) % 360; //Hue

            updateColor();
        }
        return true;
    }

    float getAngleWithYAxis(float x, float y) {
        return (float)Math.toDegrees(Math.atan2(y, x));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean formUser) {
        colorHSV[2] = progress/1000.0f; //turn whole integer value into 0-1 range
        updateColor();
    }


    //Necessary functions to implement onTouchListener
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}