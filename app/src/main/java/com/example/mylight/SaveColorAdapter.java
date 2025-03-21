package com.example.mylight;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class SaveColorAdapter extends BaseAdapter {

    private ArrayList<Integer> colorList;
    private Context context;

    public SaveColorAdapter(Context context, ArrayList<Integer> colorList){
        this.context = context;
        this.colorList = colorList;
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
        Button button;

        if(view == null){
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            button.setBackgroundColor(colorList.get(i));
        } else{
            button = (Button)view;
        }

        return button;
    }
}
