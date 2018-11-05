package com.example.ankur.homework4;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GyroscopeActivity extends AppCompatActivity implements SensorEventListener {

    private TextView mTextView;
    private Button mButton;

    private Sensor mGyroscopeSensor;
    private SensorManager mSensorManager;

    private MySQliteOpenHelper mMySQliteOpenHelper;

    private float mX;
    private float mY;
    private float mZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        mMySQliteOpenHelper = new MySQliteOpenHelper(getApplicationContext());

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Toast.makeText(getApplicationContext(), "mGyroscopeSensorManager created", Toast.LENGTH_SHORT).show();
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //Toast.makeText(getApplicationContext(), "mGyroscopeSensor created", Toast.LENGTH_SHORT).show();

        mButton = findViewById(R.id.ga_r);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert data into Gyroscope table
                Long timeStamp = System.currentTimeMillis()/1000;
                mMySQliteOpenHelper.insertInGyroscopeTable(Float.toString(mX), Float.toString(mY),
                        Float.toString(mZ), timeStamp.toString() );

                Toast.makeText(getApplicationContext(), "Added in Gyroscope table", Toast.LENGTH_SHORT).show();
            }
        });

        mButton = findViewById(R.id.ga_v);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("TABLE", "gyr");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            mX = Math.round(sensorEvent.values[0] * 100.0f) / 100.0f;
            mY = Math.round(sensorEvent.values[1] * 100.0f) / 100.0f;
            mZ = Math.round(sensorEvent.values[2] * 100.0f) / 100.0f;

            String text = "";

            mTextView = findViewById(R.id.ga_x);
            text = "X: " + mX;
            mTextView.setText(text);

            mTextView = findViewById(R.id.ga_y);
            text = "Y: " + mY;
            mTextView.setText(text);

            mTextView = findViewById(R.id.ga_z);
            text = "Z: " + mZ;
            mTextView.setText(text);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
