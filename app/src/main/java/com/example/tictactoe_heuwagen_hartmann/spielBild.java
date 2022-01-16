package com.example.tictactoe_heuwagen_hartmann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class spielBild extends AppCompatActivity {

    private TicTacToeFeld ticTacToeFeld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spiel_bild);

        Button nochmalSpielenButton = findViewById(R.id.nochmal_spielen_button);
        Button homeButton = findViewById(R.id.home_button);
        TextView spielerBild = findViewById(R.id.spielerBild);

        nochmalSpielenButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);

        String[] spielerNamen = getIntent().getStringArrayExtra("SPIELER_NAMEN");

        if(spielerNamen != null){
            spielerBild.setText((spielerNamen[0] + "'s Zug"));
        }

        ticTacToeFeld = findViewById(R.id.ticTacToeFeld);

        ticTacToeFeld.spielErstellen(nochmalSpielenButton, homeButton, spielerBild, spielerNamen);
    }

    public void nochmalSpielenButtonClick(View view) {
        ticTacToeFeld.resetSpiel();
        ticTacToeFeld.invalidate();
    }

    public void homeButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}