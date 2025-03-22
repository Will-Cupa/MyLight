package com.example.mylight;



import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mylight.fragments.SavedColorFragment;
import com.example.mylight.views.SavedColorButton;

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
        view = new SavedColorButton(owner.getContext(), i);

        view.setOnClickListener(owner);
        view.setBackgroundColor(colorList.get(i));

        return view;
    }
}
