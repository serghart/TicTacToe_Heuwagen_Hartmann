package com.example.tictactoe_heuwagen_hartmann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Button Spielen Methode

    public void playButtonClick(View view) {
        Intent intent = new Intent(this, spieler_einrichtung.class);
        startActivity(intent);
    }

    public void statiscticsButtonClick(View view){
        Intent intent = new Intent(this, statisctics.class);
        startActivity(intent);
    }

}