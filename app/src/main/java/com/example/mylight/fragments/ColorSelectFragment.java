package com.example.mylight.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mylight.MainActivity;
import com.example.mylight.R;

public class ColorSelectFragment extends Fragment implements View.OnTouchListener {
    protected int lampColor;
    protected int fragmentId;
    private int centerX, centerY, radius;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() != null){
            lampColor = ((MainActivity)getActivity()).getLampColor();
        }

        View ColorWheel = view.findViewById(R.id.ColorWheel);

        centerX = ColorWheel.getWidth()/2;
        centerY = ColorWheel.getHeight()/2;


        ColorWheel.setOnTouchListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(fragmentId, container, false);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int) event.getX();
            int y = (int) event.getY();

            Log.d("ColorWheel", String.valueOf(getAngleWithYAxis(x - centerX, y - centerY)));
        }
        ((MainActivity)getActivity()).updateColor(0);
        return true;
    }

    double getAngleWithYAxis(double pointY) {
        return Math.toDegrees(Math.acos((pointY - centerY) / radius));; // Convert to degrees
    }
}
