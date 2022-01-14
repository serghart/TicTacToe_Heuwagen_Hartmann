package com.example.tictactoe_heuwagen_hartmann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class spieler_einrichtung extends AppCompatActivity {

    private EditText spieler1;
    private EditText spieler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spieler_einrichtung);

        spieler1 = findViewById(R.id.spieler1Name);
        spieler2 = findViewById(R.id.spieler2Name);
    }

    public void speichernButtonClick(View view){
        String spieler1Name = spieler1.getText().toString();
        String spieler2Name = spieler2.getText().toString();

        Intent intent = new Intent(this, spielBild.class);
        intent.putExtra("SPIELER_NAMEN", new String[] {spieler1Name, spieler2Name});
        startActivity(intent);
    }
}