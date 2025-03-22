package com.example.mylight;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.mylight.fragments.SavedColorFragment;

import java.util.ArrayList;

public class SaveColorAdapter extends BaseAdapter {
    private ArrayList<Integer> colorList;
    private Context context;

    private SavedColorFragment owner;

    public SaveColorAdapter(Context context, ArrayList<Integer> colorList, SavedColorFragment owner){
        this.context = context;
        this.colorList = colorList;
        this.owner = owner;
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public Object getItem(int i) {
        return colorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.saved_color_button, viewGroup, false);

            view.setOnClickListener(owner);
            view.setBackgroundColor(colorList.get(i));
        }

        return view;
    }
}
