package com.example.mylight.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylight.MainActivity;
import com.example.mylight.R;
import com.example.mylight.SaveColorAdapter;

import java.util.ArrayList;

public class SavedColorFragment extends ColorSelectFragment implements View.OnClickListener {
    private ArrayList<Integer> colors;
    private SaveColorAdapter adapter;

    public SavedColorFragment(){
        fragmentId = R.layout.fragment_saved_color;
        colors = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        owner = (MainActivity)getActivity();

        setColorList();
        adapter = new SaveColorAdapter(getContext(), colors, this);

        GridView grid = (GridView) view.findViewById(R.id.colorList);
        grid.setAdapter(adapter);

        view.findViewById(R.id.saveButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.saveButton) {
            colors.add(owner.getLampColor());

            SharedPreferences sharedPref = owner.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("COLOR_"+(colors.size() - 1), owner.getLampColor());
            editor.putInt("COLOR_NUMBER", colors.size());

            editor.apply();
            adapter.notifyDataSetChanged();
        } else {
            owner.updateColor(colors.get(0));
            //Here we shouldn't retrieve the color from the layout,
            // but since it's created dynamically it's the same thing as accessing from the model
            // but it's more simple and let me avoid adding references
        }
    }

    public void setColorList(){
        SharedPreferences sharedPref = owner.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = -1;

        int colorNumber = sharedPref.getInt("COLOR_NUMBER", defaultValue);

        for(int i = 0; i < colorNumber; i++){
            colors.add(i, sharedPref.getInt("COLOR_" + i, defaultValue));
        }
    }
}
