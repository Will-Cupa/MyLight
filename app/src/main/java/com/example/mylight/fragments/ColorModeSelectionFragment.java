package com.example.mylight.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mylight.MainActivity;
import com.example.mylight.R;

public class ColorModeSelectionFragment extends Fragment implements View.OnClickListener{

    private MainActivity owner;

    public ColorModeSelectionFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_mode_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        owner = ((MainActivity)getActivity());

        ViewGroup main = (ViewGroup) view;

        for (int i = 0; i < main.getChildCount(); i++) {
            View child = main.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View button) {
        int id = button.getId();
        if(id == R.id.rgbSelect){
            owner.setColorSelectFragment(new RGBSelectFragment());
        }else if(id == R.id.hsvSelect){
            owner.setColorSelectFragment(new ColorPickerFragment());
        }else if(id == R.id.savedColors){

        }
    }
}