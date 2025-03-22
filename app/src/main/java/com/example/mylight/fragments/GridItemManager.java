package com.example.mylight.fragments;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mylight.views.SavedColorButton;

import java.util.ArrayList;

public class GridItemManager extends BaseAdapter {
    private ArrayList<Integer> colorList;
    private Context context;

    private SavedColorFragment owner;

    public GridItemManager(Context context, ArrayList<Integer> colorList, SavedColorFragment owner){
        this.context = context;
        this.colorList = colorList; //List of colors as integer
        this.owner = owner; //the owner of the manager (the fragment that uses it)
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
        //Create a new button
        view = new SavedColorButton(owner.getContext(), i);

        //Add the owner as a listener to the button
        view.setOnClickListener(owner);

        //Set background to corresponding color
        view.setBackgroundColor(colorList.get(i));

        //When returning the view, it will add it to the gridView
        return view;
    }
}
