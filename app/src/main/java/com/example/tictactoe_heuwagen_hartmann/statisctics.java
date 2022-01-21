package com.example.tictactoe_heuwagen_hartmann;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class statisctics extends AppCompatActivity {

    private DBHandler dbHandler;
    private ArrayList<SpielerStatistik> gewinner;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* set view */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statisctics);

        /* initialize properties */
        this.dbHandler = new DBHandler(statisctics.this);
        this.gewinner = dbHandler.getWinners();

        /* get layout view and prepare text view */
        this.linearLayout = findViewById(R.id.winnerContainer);
        String text = "";
        TextView view;

        /* fill layout with text views containing the player statistics data */
        for (int i = 0; i < gewinner.size(); i++) {
            text = "Spieler: " + gewinner.get(i).getName() + "     Gewinne: " + gewinner.get(i).getWins();
            view = new TextView(statisctics.this);
            view.setText(text);
            this.linearLayout.addView(view);
        }
    }
}