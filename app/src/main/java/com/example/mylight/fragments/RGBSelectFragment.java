package com.example.mylight.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mylight.Activities.MainActivity;
import com.example.mylight.R;

public class RGBSelectFragment extends ColorSelectFragment implements View.OnClickListener{

    public RGBSelectFragment(){
        fragmentId = R.layout.fragment_rgb_select;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get current ViewGroup
        ViewGroup main = (ViewGroup) view.findViewById(R.id.RGBSelectMain);

        //Get owner
        owner = (MainActivity) getActivity();

        //Get all its children
        for (int i = 0; i < main.getChildCount(); i++) {
            View child = main.getChildAt(i);
            if (child instanceof Button) {
                //if it's button, add the listener
                child.setOnClickListener(this);
            }
        }
    }


    public void onClick(View button) {
        //Separate component from the lamp color
        int red = Color.red(lampColor);
        int green = Color.green(lampColor);
        int blue = Color.blue(lampColor);

        int id = button.getId();

        //Add or remove from component based on button
        if (id == R.id.addRed) {
            red += 10;

            if(red > 255){
                red = 255;
            }
        } else if (id == R.id.remRed) {
            red -= 10;

            if(red < 0){
                red = 0;
            }
        } else if (id == R.id.addGreen) {
            green += 10;

            if(green > 255){
                green = 255;
            }
        } else if (id == R.id.remGreen) {
            green -= 10;

            if(green < 0){
                green = 0;
            }
        } else if (id == R.id.addBlue) {
            blue += 10;

            if(blue > 255){
                blue = 255;
            }
        } else if (id == R.id.remBlue) {
            blue -= 10;

            if(blue < 0){
                blue = 0;
            }
        }

        //Recombine color
        lampColor = Color.rgb(red, green, blue);

        owner.updateColor(lampColor);
    }
}