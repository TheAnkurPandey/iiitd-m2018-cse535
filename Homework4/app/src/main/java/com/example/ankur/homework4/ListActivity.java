package com.example.ankur.homework4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MySQliteOpenHelper mMySQliteOpenHelper;

    ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        String table = getIntent().getExtras().getString("TABLE");
        if (table.equals("acc"))
            readFromAcclerometerTable();
        else if (table.equals("gyr"))
            readFromGyroscopeTable();
        else if (table.equals("ori"))
            readFromOrientationTable();
        else if (table.equals("gps"))
            readFromGPSTable();
        else if (table.equals("pro"))
            readFromProximityTable();

        mRecyclerView = findViewById(R.id.la_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(this, mData));
    }

    private void readFromAcclerometerTable() {
        mMySQliteOpenHelper = new MySQliteOpenHelper(this);
        Cursor cursor = mMySQliteOpenHelper.getDataFromAcclerometerTable();

         mData = new ArrayList<>();

        while (cursor.moveToNext()) {
            mData.add("X: " + cursor.getString(1) + " " +
                    "Y: " + cursor.getString(2) + " " +
                    "Z: " + cursor.getString(3) + " " +
                    "timeStamp: " + cursor.getString(4)
            );

        }
    }

    private void readFromGyroscopeTable() {
        mMySQliteOpenHelper = new MySQliteOpenHelper(this);
        Cursor cursor = mMySQliteOpenHelper.getDataFromGyroscopeTable();

        mData = new ArrayList<>();

        while (cursor.moveToNext()) {
            mData.add("X: " + cursor.getString(1) + " " +
                    "Y: " + cursor.getString(2) + " " +
                    "Z: " + cursor.getString(3) + " " +
                    "timeStamp: " + cursor.getString(4)
            );

        }
    }

    private void readFromOrientationTable() {
        mMySQliteOpenHelper = new MySQliteOpenHelper(this);
        Cursor cursor = mMySQliteOpenHelper.getDataFromOrientationTable();

        mData = new ArrayList<>();

        while (cursor.moveToNext()) {
            mData.add("X: " + cursor.getString(1) + " " +
                    "Y: " + cursor.getString(2) + " " +
                    "Z: " + cursor.getString(3) + " " +
                    "timeStamp: " + cursor.getString(4)
            );

        }
    }

    private void readFromGPSTable() {
        mMySQliteOpenHelper = new MySQliteOpenHelper(this);
        Cursor cursor = mMySQliteOpenHelper.getDataFromGPSTable();

        mData = new ArrayList<>();

        while (cursor.moveToNext()) {
            mData.add("Longitude: " + cursor.getString(1) + " " +
                    "Latitude: " + cursor.getString(2) + " " +
                    "timeStamp: " + cursor.getString(3)
            );

        }
    }

    private void readFromProximityTable() {
        mMySQliteOpenHelper = new MySQliteOpenHelper(this);
        Cursor cursor = mMySQliteOpenHelper.getDataFromProximityTable();

        mData = new ArrayList<>();

        while (cursor.moveToNext()) {
            mData.add("IsClose: " + cursor.getString(1) + " " +
                    "timeStamp: " + cursor.getString(2)
            );

        }
    }
}
