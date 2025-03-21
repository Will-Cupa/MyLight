package com.example.mylight.fragments;

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

        adapter = new SaveColorAdapter(getContext(), colors);

        owner = (MainActivity)getActivity();

        GridView grid = (GridView) view.findViewById(R.id.colorList);
        grid.setAdapter(adapter);

        view.findViewById(R.id.saveButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        colors.add(owner.getLampColor());
        adapter.notifyDataSetChanged();
    }
}
