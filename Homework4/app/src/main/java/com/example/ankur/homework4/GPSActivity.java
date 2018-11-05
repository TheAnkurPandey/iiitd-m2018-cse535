package com.example.ankur.homework4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends AppCompatActivity implements LocationListener {

    private Button mButton;
    private TextView mTextView;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private double mLongitude;
    private double mLatitude;

    private MySQliteOpenHelper mMySQliteOpenHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        mMySQliteOpenHelper = new MySQliteOpenHelper(getApplicationContext());

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = this;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 15);
            return;
        }

        mButton = findViewById(R.id.gpsa_r);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert data into GPS table
                Long timeStamp = System.currentTimeMillis()/1000;
                mMySQliteOpenHelper.insertInGPSTable(Double.toString(mLongitude), Double.toString(mLatitude), timeStamp.toString());
                Toast.makeText(getApplicationContext(), "Added in GPS table", Toast.LENGTH_SHORT).show();
            }
        });

        mButton = findViewById(R.id.gpsa_v);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("TABLE", "gps");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 15 &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            mButton = findViewById(R.id.gpsa_gl);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLocationManager.requestLocationUpdates("gps", 5000, 0, mLocationListener);
                }
            });
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String txt = "";

        mTextView = findViewById(R.id.gpsa_long);
        mLongitude = location.getLongitude();
        txt = "Longitude: " + location.getLongitude();
        mTextView.setText(txt);

        mTextView = findViewById(R.id.gpsa_lat);
        mLatitude = location.getLatitude();
        txt = "Latitude: " + location.getLatitude();
        mTextView.setText(txt);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}
