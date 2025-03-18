package com.example.mylight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Accueil extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST","started");
        EdgeToEdge.enable(this);
        setContentView(R.layout.accueil_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewGroup main = (ViewGroup) findViewById(R.id.main);
        for(int i=0; i < main.getChildCount(); i++){
            View child = main.getChildAt(i);
            if (child instanceof Button){
                child.setOnClickListener(this);
            }
        }
    }


    public void onClick(View button){
        int id = button.getId();
        int color = 0;
        if(id == R.id.buttonRed) {
            color = getResources().getColor(R.color.red);
        } else if(id == R.id.buttonGreen){
            color = getResources().getColor(R.color.green);
        } else if(id == R.id.buttonBlue){
            color= getResources().getColor(R.color.blue);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("color", color);
        startActivity(intent);
    }
}