package com.example.mylight.views;

import android.content.Context;

public class SavedColorButton extends androidx.appcompat.widget.AppCompatButton {

    private int id;

    //Custom button class, to store its id in the grid
    public SavedColorButton(Context context, int id) {
        super(context);
        this.id = id; //id in the gridView
    }

    public int getIndex(){
        return id;
    }
}
