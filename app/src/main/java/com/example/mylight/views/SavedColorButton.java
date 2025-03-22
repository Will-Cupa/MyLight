package com.example.mylight.views;

import android.content.Context;

public class SavedColorButton extends androidx.appcompat.widget.AppCompatButton {

    private int id;

    public SavedColorButton(Context context, int id) {
        super(context);
        this.id = id;
    }

    public int getIndex(){
        return id;
    }
}
