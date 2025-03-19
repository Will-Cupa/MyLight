package com.example.mylight;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] buttonList;
    private Button preview;
    private Thread rainbowThread, serverThread;
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
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                lampColor = extras.getInt("color");
            } else {
                lampColor = 0;
            }
        }

        ViewGroup main = (ViewGroup) findViewById(R.id.main);

        for(int i=0; i < main.getChildCount(); i++){
            View child = main.getChildAt(i);
            if (child instanceof Button){
                if(child.getId() == R.id.colorPreview){
                    preview = (Button) child;
                    preview.setBackgroundColor(lampColor);
                    updateTextColor(lampColor);
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
            if(rainbowThread == null || rainbowThread.getState() == Thread.State.TERMINATED){
                rainbowThread = new RainbowThread(lampColor, 7);
                rainbowThread.start();
            }

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

        updateColor(lampColor);
    }


    public void updateColor(int color){
        updateTextColor(color);
        preview.setBackgroundColor(color);

        if(serverThread == null || serverThread.getState() == Thread.State.TERMINATED){
            serverThread = new ServerThread(color);
            serverThread.start();
        }
    }

    public void updateTextColor(int color){
        if(Color.luminance(color) <= 0.5){
            preview.setTextColor(getResources().getColor(R.color.white));
        }else{
            preview.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private class RainbowThread extends Thread {
        private final int colorSet[];
        private final Handler handler;

        private boolean started;

        public RainbowThread(int initialColor, int colorNumber){
            Log.d("Thread","thread created");
            colorSet = new int[colorNumber + 1];
            handler = new Handler();
            randomColors();
            colorSet[0] = initialColor;
            colorSet[colorNumber] = initialColor;
        }

        public void randomColors(){
            int red, green, blue;
            for(int i = 1; i < colorSet.length - 1; i++){
                red = (int) (Math.random() * 255);
                green = (int) (Math.random() * 255);
                blue = (int) (Math.random() * 255);

                colorSet[i] =  Color.rgb(red, green, blue);
                Log.d("color", String.valueOf(colorSet[i]));
            }
        }

        public int lerpColor(int i, float factor){
            int red = lerpChannel(Color.red(colorSet[i]),Color.red(colorSet[i+1]), factor);
            int green = lerpChannel(Color.green(colorSet[i]),Color.green(colorSet[i+1]), factor);
            int blue = lerpChannel(Color.blue(colorSet[i]),Color.blue(colorSet[i+1]), factor);

            return Color.rgb(red,green,blue);
        }

        public int lerpChannel(int chan1, int chan2, float factor){
            return (int)((chan2 - chan1) * factor + chan1);
        }

        @Override
        public void run(){
            Log.d("Thread","thread started");
            for(int i = 0; i < colorSet.length - 1; i++) {
                float fraction = 0;
                while(fraction < 1){
                    int color = lerpColor(i,fraction);
                    fraction += 0.01;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateColor(color);
                        }
                    });

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private class ServerThread extends Thread {
        private final Handler handler = new Handler();
        private final int color;

        public ServerThread(int color){
            this.color = color;
        }

        public String formatChannel(int channel){
            String result;
            result = Integer.toHexString(channel);
            if(result.length() < 2){
                result = "0" + result;
            }
            return result;
        }

        public String formatColor(){
            return formatChannel(Color.red(color)) + formatChannel(Color.green(color)) + formatChannel(Color.blue(color));
        }

        public void run() {
            try {
                // Tentative de connexion au server
                Socket socket = new Socket("chadok.info", 9998);
                // Flux sortant et entrant
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Envoi des deux paramètres au serveur
                writer.println("01" + formatColor());
                Log.d("server", formatColor());

                try {
                    // Gestion de l'interruption potentielle tant que le serveur n'a pas répondu
                    while (!reader.ready()) {
                        Thread.sleep(500);
                    }
                    // Lecture du résultat
                    Log.i("server",reader.readLine());
                    // Affichage du résultat
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } catch (InterruptedException e) {
                    // Cas où l'utilisateurice a demandé l'interruption
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
                socket.close();
            } catch (IOException e) {
                // Cas où la connexion au serveur a échoué
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }
}