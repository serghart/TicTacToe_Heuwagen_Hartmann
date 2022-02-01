package com.example.tictactoe_heuwagen_hartmann;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class statisctics extends AppCompatActivity {

    private DBHandler dbHandler;
    private ArrayList<SpielerStatistik> gewinner;
    private LinearLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* set view */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statisctics);

        /* initialize properties */
        this.dbHandler = new DBHandler(statisctics.this);
        this.gewinner = dbHandler.getWinners();

        /* get layout view and prepare text view */
        this.tableLayout = findViewById(R.id.winnerContainer);

        /* fill layout with text views containing the player statistics data */
        for (int i = 0; i < gewinner.size(); i++) {
            this.tableLayout.addView(this.createRowFromData(gewinner.get(i), i+1));
        }
    }

    private TableRow createRowFromData(SpielerStatistik stat, int position) {
        /* create new table row */
        TableRow tr = new TableRow(statisctics.this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView pos = new TextView(statisctics.this);
        pos.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1));
        pos.setText(position + ".");
        pos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        pos.setTextSize(16);

        /* init name col */
        TextView name = new TextView(statisctics.this);
        name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1));
        name.setText(stat.getName());
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        name.setTextSize(16);

        /* init wins col */
        TextView wins = new TextView(statisctics.this);
        String count = stat.getWins() + ""; /* work around as the text view won't accept ints */
        wins.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1));
        wins.setText(count);
        wins.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        wins.setTextSize(16);

        /* add cols to row */
        tr.addView(pos);
        tr.addView(name);
        tr.addView(wins);

        return tr;
    }
}