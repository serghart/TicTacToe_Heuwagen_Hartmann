package com.example.tictactoe_heuwagen_hartmann;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "tictactoe";

    private static final String TABLE_NAME = "games";

    private static final String ID_COL = "id";

    private static final String WINNER_NAME = "winner";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* create table structure */
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WINNER_NAME + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * <summary>Schreibt den Namen des Gewinners in die DB</summary>
     *
     * @param name Name des Gewinners
     */
    public void addWinner(String name) {
        /* get db */
        SQLiteDatabase db = this.getWritableDatabase();
        /* init new values object for easier query */
        ContentValues values = new ContentValues();
        /* set values for the data */
        values.put(WINNER_NAME, name);
        /* execute db query that inserts the player data */
        db.insert(TABLE_NAME, null, values);
        /* close db to prevent unwanted db queries */
        db.close();
    }

    /**
     * <summary>Gibt ein Array mit den {@link SpielerStatistik} zurück (Spielername, Anzahl gewonnene Spiele)</summary>
     */
    public ArrayList<SpielerStatistik> getWinners() {
        /* get db */
        SQLiteDatabase db = this.getWritableDatabase();
        /* init cursor that can iterate over the query result */
        Cursor cursor = db.rawQuery("SELECT winner, count(winner) FROM " + TABLE_NAME + " GROUP by winner", null);
        /* init result list */
        ArrayList<SpielerStatistik> list = new ArrayList<>();
        /* iterate over the results and fill the result array */
        if (cursor.moveToFirst()) {
            do {
                list.add(new SpielerStatistik(cursor.getString(0), cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        /* close db connection */
        cursor.close();
        /* return the result list */
        return list;
    }

    /**
     * <summary>Helper function to delete all userdata from the statistics table</summary>
     */
    public void wipeWinners() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "", null);

        db.close();
    }
}
