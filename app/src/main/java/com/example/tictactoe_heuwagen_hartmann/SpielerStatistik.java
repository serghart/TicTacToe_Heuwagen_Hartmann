package com.example.tictactoe_heuwagen_hartmann;

public class SpielerStatistik {

    private String name;
    private int wins;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public SpielerStatistik(String name, int wins) {
        this.name = name;
        this.wins = wins;
    }
}
