package com.example.tictactoe_heuwagen_hartmann;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class SpielLogik {
    private int[][] spielFeld;

    private String[] spielerNamen = {"Spieler 1", "Spieler 2"};

    private int[] siegTyp = {-1, -1, -1};

    private Button nochmalSpielenButton;
    private Button homeButton;
    private TextView spielerZug;

    private int spieler = 1;
    private Context context;
    private DBHandler dbHandler;

    SpielLogik(Context context) {
        spielFeld = new int[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                spielFeld[r][c] = 0;
            }
        }
        this.context = context;
        dbHandler = new DBHandler(context);
    }

    public boolean updateSpielfeld(int row, int col) {
        if (spielFeld[row - 1][col - 1] == 0) {
            spielFeld[row - 1][col - 1] = spieler;

            if (spieler == 1) {
                spielerZug.setText((spielerNamen[1] + "'s Zug"));
            } else {
                spielerZug.setText((spielerNamen[0] + "'s Zug"));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean werIstSieger() {
        boolean istSieger = false;

        // Horizontaler Check
        for (int r = 0; r < 3; r++) {
            if (spielFeld[r][0] == spielFeld[r][1] && spielFeld[r][0] == spielFeld[r][2]
                    && spielFeld[r][0] != 0) {
                siegTyp = new int[]{r, 0, 1};
                istSieger = true;
            }
        }

        // Vertikaler Check
        for (int c = 0; c < 3; c++) {
            if (spielFeld[0][c] == spielFeld[1][c] && spielFeld[2][c] == spielFeld[0][c]
                    && spielFeld[0][c] != 0) {
                siegTyp = new int[]{0, c, 2};
                istSieger = true;
            }
        }

        // Diagonaler Check negative Linie
        if (spielFeld[0][0] == spielFeld[1][1] && spielFeld[0][0] == spielFeld[2][2]
                && spielFeld[0][0] != 0) {
            siegTyp = new int[]{0, 2, 3};
            istSieger = true;
        }

        // Diagonaler Check positive Linie
        if (spielFeld[2][0] == spielFeld[1][1] && spielFeld[2][0] == spielFeld[0][2]
                && spielFeld[2][0] != 0) {
            siegTyp = new int[]{2, 2, 4};
            istSieger = true;
        }

        int felderGefuellt = 0;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (spielFeld[r][c] != 0) {
                    felderGefuellt += 1;
                }
            }
        }

        if (istSieger) {
            nochmalSpielenButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            spielerZug.setText((spielerNamen[spieler - 1] + " hat gewonnen!"));

            /* entry db */
            dbHandler.addWinner(spielerNamen[spieler - 1]);
            return true;
        } else if (felderGefuellt == 9) {
            nochmalSpielenButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            spielerZug.setText("Unentschieden!");
            /* no db entry needed */
            return true;
        } else {
            return false;
        }
    }

    public void resetSpiel() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                spielFeld[r][c] = 0;
            }
        }

        spieler = 1;
        nochmalSpielenButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        spielerZug.setText((spielerNamen[0] + "'s Zug"));
    }

    public void setNochmalSpielenButton(Button nochmalSpielenButton) {
        this.nochmalSpielenButton = nochmalSpielenButton;
    }

    public void setHomeButton(Button homeButton) {
        this.homeButton = homeButton;
    }

    public void setSpielerZug(TextView spielerZug) {
        this.spielerZug = spielerZug;
    }

    public void setSpielerNamen(String[] spielerNamen) {
        this.spielerNamen = spielerNamen;
    }

    public int[][] getSpielFeld() {
        return spielFeld;
    }

    public void setSpieler(int spieler) {
        this.spieler = spieler;
    }

    public int getSpieler() {
        return spieler;
    }

    public int[] getSiegTyp() {
        return siegTyp;
    }
}
