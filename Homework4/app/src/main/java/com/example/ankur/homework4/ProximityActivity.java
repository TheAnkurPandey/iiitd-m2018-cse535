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

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    private Button mButton;
    private TextView mTextView;

    private SensorManager mSensorManager;
    private Sensor mProximitySensor;

    private String mIsClose;

    private MySQliteOpenHelper mMySQliteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        mMySQliteOpenHelper = new MySQliteOpenHelper(getApplicationContext());

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null)
            mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        else
            Toast.makeText(getApplicationContext(), "No access to SensorManager", Toast.LENGTH_SHORT).show();

        mButton = findViewById(R.id.pa_r);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long timeStamp = System.currentTimeMillis()/1000;
                mMySQliteOpenHelper.insertInProximityTable(mIsClose, timeStamp.toString() );
                Toast.makeText(getApplicationContext(), "Added in Proximity table", Toast.LENGTH_SHORT).show();
            }
        });

        mButton = findViewById(R.id.pa_v);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("TABLE", "pro");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        mTextView = findViewById(R.id.pa_tv);
        if(sensorEvent.values[0] < mProximitySensor.getMaximumRange()) {
            mIsClose = "True";
            mTextView.setText("True");
        }
        else {
            mIsClose = "False";
            mTextView.setText("False");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
