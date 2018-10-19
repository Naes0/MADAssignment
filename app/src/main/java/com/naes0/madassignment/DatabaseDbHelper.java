package com.naes0.madassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Camera;

import com.naes0.madassignment.DatabaseSchema.*;

public class DatabaseDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "database.db";
    
    public DatabaseDbHelper(Context c)
    {
        super(c, DATABASE_NAME, null, VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + PlayerTable.NAME + "(" + "_id integer primary key autoincrement, " +
                PlayerTable.Cols.ID + ", " +
                PlayerTable.Cols.ROW + ", " +
                PlayerTable.Cols.COL + ", " +
                PlayerTable.Cols.CASH + ", " +
                PlayerTable.Cols.HEALTH + ", " +
                PlayerTable.Cols.EQUIPMASS + ", " +
                PlayerTable.Cols.EQUIPMENT + ")");

        db.execSQL("CREATE TABLE " + AreaTable.NAME + "(" + "_id integer primary key autoincrement, " +
                AreaTable.Cols.ID + ", " +
                AreaTable.Cols.TOWN + ", " +
                AreaTable.Cols.DESC + ", " +
                AreaTable.Cols.STARRED + ", " +
                AreaTable.Cols.EXPLORED + ", " +
                AreaTable.Cols.UNEXP + ", " +
                AreaTable.Cols.UNSTAR + ", " +
                AreaTable.Cols.ITEMS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
