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

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private Button mButton;
    private TextView mTextView;

    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;

    private float mX;
    private float mY;
    private float mZ;

    private float lastX = 0;
    private float lastY = 0;
    private float lastZ = 0;

    private long lastUpdate = 0;

    private MySQliteOpenHelper mMySQliteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        mMySQliteOpenHelper = new MySQliteOpenHelper(getApplicationContext());

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null)
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        else
            Toast.makeText(getApplicationContext(), "No access to SensorManager", Toast.LENGTH_SHORT).show();

        mButton = findViewById(R.id.aa_r);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert data into Accelerometer table
                Long timeStamp = System.currentTimeMillis()/1000;
                mMySQliteOpenHelper.insertInAccelerometerTable(Float.toString(mX), Float.toString(mY),
                        Float.toString(mZ), timeStamp.toString() );

                Toast.makeText(getApplicationContext(), "Added in Accelerometer table", Toast.LENGTH_SHORT).show();
            }
        });

        mButton = findViewById(R.id.aa_v);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("TABLE", "acc");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            mX = Math.round(sensorEvent.values[0] * 100.0f) / 100.0f;
            mY = Math.round(sensorEvent.values[1] * 100.0f) / 100.0f;
            mZ = Math.round(sensorEvent.values[2] * 100.0f) / 100.0f;

            String text = "";

            mTextView = findViewById(R.id.aa_x);
            text = "X: " + mX;
            mTextView.setText(text);

            mTextView = findViewById(R.id.aa_y);
            text = "Y: " + mY;
            mTextView.setText(text);

            mTextView = findViewById(R.id.aa_z);
            text = "Z: " + mZ;
            mTextView.setText(text);

        }

        long curTime = System.currentTimeMillis();

        if (lastUpdate == 0)
            lastUpdate = curTime;

        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float speed = Math.abs(mX+mY+mZ - lastX - lastY - lastZ) / diffTime * 10000;

            if (speed > 4000) {
                Toast.makeText(this, "shake detected", Toast.LENGTH_SHORT).show();
            }
            lastX = mX;
            lastY = mY;
            lastZ = mZ;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
