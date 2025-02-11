package com.example.mylight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] buttonList;
    private Button preview;

    private int lampColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Toolbar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonList = new Button[6];
        if(savedInstanceState != null){
            lampColor = savedInstanceState.getInt("LAMP COLOR");
        }else {
            lampColor = getResources().getColor(R.color.red);
        }

        ViewGroup main = (ViewGroup) findViewById(R.id.main);

        for(int i=0; i < main.getChildCount(); i++){
            View child = main.getChildAt(i);
            if (child instanceof Button){
                if(child.getId() == R.id.colorPreview){
                    preview = (Button) child;
                    preview.setBackgroundColor(lampColor);
                    updateTextColor();
                } else {
                    buttonList[i] = (Button) child;
                    child.setOnClickListener(this);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("LAMP COLOR", lampColor);
    }

        @Override
    public void onClick(View button) {
        int red = Color.red(lampColor);
        int green = Color.green(lampColor);
        int blue = Color.blue(lampColor);

        int id = button.getId();

        if (id == R.id.addRed) {
            red += 10;

            if(red > 255){
                red = 255;
            }
        } else if (id == R.id.remRed) {
            red -= 10;

            if(red < 0){
                red = 0;
            }
        } else if (id == R.id.addGreen) {
            green += 10;

            if(green > 255){
                green = 255;
            }
        } else if (id == R.id.remGreen) {
            green -= 10;

            if(green < 0){
                green = 0;
            }
        } else if (id == R.id.addBlue) {
            blue += 10;

            if(blue > 255){
                blue = 255;
            }
        } else if (id == R.id.remBlue) {
            blue -= 10;

            if(blue < 0){
                blue = 0;
            }
        }

        lampColor = Color.rgb(red, green, blue);

        updateTextColor();
        preview.setBackgroundColor(lampColor);
    }

    public void updateTextColor(){
        if(Color.luminance(lampColor) <= 0.5){
            preview.setTextColor(getResources().getColor(R.color.white));
        }else{
            preview.setTextColor(getResources().getColor(R.color.black));
        }
    }
}