package com.example.ankur.homework4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQliteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SensorDB";

    private static final String A_TABLE = "AccelerometerTable";
    private static final String G_TABLE = "GyroscopeTable";
    private static final String O_TABLE = "OrientationTable";
    private static final String GPS_TABLE = "GPSTable";
    private static final String P_TABLE = "ProximityTable";

    private static final String COLUMN_X = "X";
    private static final String COLUMN_Y = "Y";
    private static final String COLUMN_Z = "Z";

    private static final String COLUMN_LONG = "Longitude";
    private static final String COLUMN_LAT = "Latitude";

    private static final String COLUMN_ISCLOSE = "IsClose";

    private static final String COLUMN_TIME_STAMP = "TimeStamp";

    public MySQliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQLQuery = "CREATE TABLE " + A_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_X + " TEXT, " +
                COLUMN_Y + " TEXT, " +
                COLUMN_Z + " TEXT, " +
                COLUMN_TIME_STAMP + " TEXT )";

        sqLiteDatabase.execSQL(SQLQuery);

        SQLQuery = "CREATE TABLE " + G_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_X + " TEXT, " +
                COLUMN_Y + " TEXT, " +
                COLUMN_Z + " TEXT, " +
                COLUMN_TIME_STAMP + " TEXT )";

        sqLiteDatabase.execSQL(SQLQuery);

        SQLQuery = "CREATE TABLE " + O_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_X + " TEXT, " +
                COLUMN_Y + " TEXT, " +
                COLUMN_Z + " TEXT, " +
                COLUMN_TIME_STAMP + " TEXT )";

        sqLiteDatabase.execSQL(SQLQuery);

        SQLQuery = "CREATE TABLE " + GPS_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LONG + " TEXT, " +
                COLUMN_LAT + " TEXT, " +
                COLUMN_TIME_STAMP + " TEXT )";

        sqLiteDatabase.execSQL(SQLQuery);

        SQLQuery = "CREATE TABLE " + P_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ISCLOSE + " TEXT, " +
                COLUMN_TIME_STAMP + " TEXT )";

        sqLiteDatabase.execSQL(SQLQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertInAccelerometerTable(String x, String y, String z, String timeStamp) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_X, x);
        contentValues.put(COLUMN_Y, y);
        contentValues.put(COLUMN_Z, z);
        contentValues.put(COLUMN_TIME_STAMP, timeStamp);

        sqLiteDatabase.insert(A_TABLE, null, contentValues);
    }

    public void insertInGyroscopeTable(String x, String y, String z, String timeStamp) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_X, x);
        contentValues.put(COLUMN_Y, y);
        contentValues.put(COLUMN_Z, z);
        contentValues.put(COLUMN_TIME_STAMP, timeStamp);

        sqLiteDatabase.insert(G_TABLE, null, contentValues);
    }

    public void insertInOrintationTable(String x, String y, String z, String timeStamp) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_X, x);
        contentValues.put(COLUMN_Y, y);
        contentValues.put(COLUMN_Z, z);
        contentValues.put(COLUMN_TIME_STAMP, timeStamp);

        sqLiteDatabase.insert(O_TABLE, null, contentValues);
    }

    public void insertInGPSTable(String longitude, String latitude, String timeStamp) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_LONG, longitude);
        contentValues.put(COLUMN_LAT, latitude);
        contentValues.put(COLUMN_TIME_STAMP, timeStamp);

        sqLiteDatabase.insert(GPS_TABLE, null, contentValues);
    }

    public void insertInProximityTable(String isClose, String timeStamp) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ISCLOSE, isClose);
        contentValues.put(COLUMN_TIME_STAMP, timeStamp);

        long status = sqLiteDatabase.insert(P_TABLE, null, contentValues);
        Log.v("my_f", "" + status);
    }

    public Cursor getDataFromAcclerometerTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sqlQuery = " SELECT * FROM " + A_TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        return cursor;
    }

    public Cursor getDataFromGyroscopeTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sqlQuery = " SELECT * FROM " + G_TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        return cursor;
    }

    public Cursor getDataFromOrientationTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sqlQuery = " SELECT * FROM " + O_TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        return cursor;
    }

    public Cursor getDataFromGPSTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sqlQuery = " SELECT * FROM " + GPS_TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        return cursor;
    }

    public Cursor getDataFromProximityTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sqlQuery = " SELECT * FROM " + P_TABLE;

        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        return cursor;
    }
}
