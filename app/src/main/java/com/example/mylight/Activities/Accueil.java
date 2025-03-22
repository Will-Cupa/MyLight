package com.example.mylight.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mylight.R;

public class Accueil extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.accueil_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Get main ViewGroup
        ViewGroup main = (ViewGroup) findViewById(R.id.main);

        //Get all its children
        for(int i=0; i < main.getChildCount(); i++){
            View child = main.getChildAt(i);
            if (child instanceof Button){
                //if it's button, add the listener
                child.setOnClickListener(this);
            }
        }
    }


    public void onClick(View button){
        int id = button.getId();
        int color = 0;

        //Set color based on button
        if(id == R.id.buttonRed) {
            color = ContextCompat.getColor(this, R.color.red);
        } else if(id == R.id.buttonGreen){
            color = ContextCompat.getColor(this, R.color.green);
        } else if(id == R.id.buttonBlue){
            color= ContextCompat.getColor(this, R.color.blue);;
        }

        //Create intent with the MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        //Add color to intent
        intent.putExtra("color", color);

        //Start Activity with intent
        startActivity(intent);
    }
}