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

    public void addWinner(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(WINNER_NAME, name);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<SpielerStatistik> getWinners(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT winner, count(winner) FROM " + TABLE_NAME + " GROUP by winner",null);

        ArrayList<SpielerStatistik> list = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                list.add(new SpielerStatistik(cursor.getString(0), cursor.getInt(1)));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    public void wipeWinners(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "",null);
    }
}
