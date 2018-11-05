package com.example.ankur.homework4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.ma_acc);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccelerometerActivity.class);
                startActivity(intent);
            }
        });

        mButton = findViewById(R.id.ma_gyro);
        if (mButton != null)
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), GyroscopeActivity.class);
                    startActivity(intent);
                }
            });
        else
            Toast.makeText(getApplicationContext(), "null is returned", Toast.LENGTH_SHORT).show();

        mButton = findViewById(R.id.ma_o);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrientationActivity.class);
                startActivity(intent);
            }
        });

        mButton = findViewById(R.id.ma_gps);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GPSActivity.class);
                startActivity(intent);
            }
        });

        mButton = findViewById(R.id.ma_p);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProximityActivity.class);
                startActivity(intent);
            }
        });
    }
}
