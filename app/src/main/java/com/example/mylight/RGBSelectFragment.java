package com.example.mylight;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RGBSelectFragment extends Fragment implements View.OnClickListener{
    private MainActivity owner;
    private int lampColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rgb_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() != null){
            lampColor = ((MainActivity)getActivity()).getLampColor();
        }

        ViewGroup main = (ViewGroup) view.findViewById(R.id.RGBSelectMain);

        for (int i = 0; i < main.getChildCount(); i++) {
            View child = main.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(this);
            }
        }
    }


    public void setLampColor(int color){
        lampColor = color;
    }

    public void onClick(View button) {
        int red = Color.red(lampColor);
        int green = Color.green(lampColor);
        int blue = Color.blue(lampColor);

        int id = button.getId();

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

        lampColor = Color.rgb(red, green, blue);

        ((MainActivity)getActivity()).updateColor(lampColor);
    }
}