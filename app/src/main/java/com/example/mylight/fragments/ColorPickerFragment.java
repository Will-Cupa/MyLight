package com.example.mylight.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.example.mylight.R;

public class ColorPickerFragment extends ColorSelectFragment {

    public ColorPickerFragment(){
        fragmentId = R.layout.fragment_color_picker;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}