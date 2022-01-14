package com.example.tictactoe_heuwagen_hartmann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class spielBild extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spiel_bild);
    }

    public void nochmalSpielenButtonClick(View view) {
        // bla
    }

    public void homeButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}