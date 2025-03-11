package com.example.mylight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    private Thread rainbowThread;

    private int lampColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST", "activity started!");  // Affiche un message de débogage

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Toolbar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonList = new Button[7];
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
                }
                child.setOnClickListener(this);
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

        if (id == R.id.colorPreview) {
            RainbowThread thread = new RainbowThread(lampColor, 7);
            Log.d("Thread","button clicked");
            thread.start();
        } else if (id == R.id.addRed) {
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

    private class RainbowThread extends Thread {
        private int colorSet[];
        private final Handler handler;

        public RainbowThread(int initialColor, int colorNumber){
            Log.d("Thread","thread created");
            colorSet = new int[colorNumber + 1];
            handler = new Handler();
            randomColors();
            colorSet[colorNumber] = initialColor;
        }

        public void randomColors(){
            int red, green, blue;
            for(int i = 0; i < colorSet.length - 1; i++){
                red = (int) (Math.random() * 255);
                green = (int) (Math.random() * 255);
                blue = (int) (Math.random() * 255);

                colorSet[i] =  Color.rgb(red, green, blue);
                Log.d("color", String.valueOf(colorSet[i]));
            }
        }
        @Override
        public void run(){
            Log.d("Thread","thread started");
            for(int i = 0; i < colorSet.length - 1; i++) {
                float fraction = 0;
                while( fraction < 1){
                    int color = (int)((colorSet[i+1] - colorSet[i]) * fraction + colorSet[i]);
                    fraction += 0.1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            preview.setBackgroundColor(color);
                        }
                    });

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}