package com.example.ankur.homework4;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrientationActivity extends AppCompatActivity implements SensorEventListener {

    private TextView mTextView;
    private Button mButton;

    private Sensor mAccelerometerSensor;
    private Sensor mMagnetometerSensor;
    private SensorManager mSensorManager;
    private Sensor mProximitySensor;

    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];

    private MySQliteOpenHelper mMySQliteOpenHelper;

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];


    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    private float mX;
    private float mY;
    private float mZ;
    private String mIsClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        mMySQliteOpenHelper = new MySQliteOpenHelper(getApplicationContext());

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mButton = findViewById(R.id.oa_r);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert data into Orientation table
                Long timeStamp = System.currentTimeMillis()/1000;
                mMySQliteOpenHelper.insertInOrintationTable(Float.toString(mX), Float.toString(mY),
                        Float.toString(mZ), timeStamp.toString() );

                Toast.makeText(getApplicationContext(), "Added in Orientation table", Toast.LENGTH_SHORT).show();
            }
        });

        mButton = findViewById(R.id.oa_v);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("TABLE", "ori");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLastAccelerometerSet = false;
        mLastMagnetometerSet = false;

        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(proximitySensorListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
        mSensorManager.unregisterListener(proximitySensorListener);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        /*
        Since TYPE_ORIENTATION is deprecated in API level 8, Therefore the only way is to use both
        Accelerometer and Magnetometer reading then compute RotationMatrix. In order to achieve this below snippet has been taken
        from
        https://developer.android.com/guide/topics/sensors/sensors_position
        https://stackoverflow.com/questions/10291322/what-is-the-alternative-to-android-orientation-sensor
         */

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            System.arraycopy(sensorEvent.values, 0, mAccelerometerReading,
                    0, mAccelerometerReading.length);
            mLastAccelerometerSet = true;

        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            System.arraycopy(sensorEvent.values, 0, mMagnetometerReading,
                    0, mMagnetometerReading.length);
            mLastMagnetometerSet = true;

        }

        if (mLastAccelerometerSet && mLastMagnetometerSet)
            updateOrientationAngles();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void updateOrientationAngles() {
        SensorManager.getRotationMatrix(mRotationMatrix, null,
                mAccelerometerReading, mMagnetometerReading);
        SensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

        mZ = Math.round(mOrientationAngles[0] * 100.0f) / 100.0f;
        mX = Math.round(mOrientationAngles[1] * 100.0f) / 100.0f;
        mY = Math.round(mOrientationAngles[2] * 100.0f) / 100.0f;

        String text = "";

        mTextView = findViewById(R.id.oa_x);
        text = "Pitch: " + mX;
        mTextView.setText(text);

        mTextView = findViewById(R.id.oa_y);
        text = "Roll: " + mY;
        mTextView.setText(text);

        mTextView = findViewById(R.id.oa_z);
        text = "Azimuth: " + mZ;
        mTextView.setText(text);

    }

    public SensorEventListener proximitySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.values[0] < mProximitySensor.getMaximumRange()) {
                mIsClose = "True";
                if (Math.abs(mZ - 0) < 0.4 && Math.abs(mY - 1.14) < 0.5) {
                    Toast.makeText(getApplicationContext(), "Facing south", Toast.LENGTH_LONG).show();
                } else if (Math.abs(mZ - 3.14) < 0.4 && Math.abs(mY - 1.14) < 0.5) {
                    Toast.makeText(getApplicationContext(), "Facing North", Toast.LENGTH_LONG).show();
                } else if (Math.abs(mY - 0) < 0.4) {
                    Toast.makeText(getApplicationContext(), "Nearly parallel to plane", Toast.LENGTH_LONG).show();
                }
            }
            else {
                mIsClose = "False";
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
}
