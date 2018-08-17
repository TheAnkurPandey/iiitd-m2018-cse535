package com.example.ankur.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2_A1_MT18070 extends AppCompatActivity {

    private TextView textView;
    public static final String TAG = "Tag";
    private static String prev = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2__a1__mt18070);
        
        Bundle letter = getIntent().getExtras();
        if (letter == null)
            return;
        
        String str = "";

        textView = findViewById(R.id.ma2n);
        str = "Name: " + letter.getString("name");
        textView.setText(str);

        textView = findViewById(R.id.ma2rn);
        str = "Roll No: " + letter.getString("rollno");
        textView.setText(str);

        textView = findViewById(R.id.ma2b);
        str = "Branch: " + letter.getString("branch");
        textView.setText(str);

        textView = findViewById(R.id.ma2c1);
        str = "Course1: " + letter.getString("course1");
        textView.setText(str);

        textView = findViewById(R.id.ma2c2);
        str = "Course2: " + letter.getString("course2");
        textView.setText(str);

        textView = findViewById(R.id.ma2c3);
        str = "Course3: " + letter.getString("course3");
        textView.setText(str);

        textView = findViewById(R.id.ma2c4);
        str = "Course4: " + letter.getString("course4");
        textView.setText(str);

        Log.i(TAG, "State of the MainActivity2_A1_MT18070 changed from " + prev + " Create");
        Toast.makeText(getApplicationContext(), "State of the MainActivity2_A1_MT18070 changed from " + prev + " to Create",
                Toast.LENGTH_SHORT).show();
        prev = "Create";
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "State of the MainActivity2_A1_MT18070 changed from " + prev + " Start");
        Toast.makeText(getApplicationContext(), "State of the MainActivity2_A1_MT18070 changed from " + prev + " to Start",
                Toast.LENGTH_SHORT).show();
        prev = "Start";
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "State of the MainActivity2_A1_MT18070 changed from " + prev + " Resume");
        Toast.makeText(getApplicationContext(), "State of the MainActivity2_A1_MT18070 changed from " + prev + " to Resume",
                Toast.LENGTH_SHORT).show();
        prev = "Resume";
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "State of the MainActivity2_A1_MT18070 changed from " + prev + " Pause");
        Toast.makeText(getApplicationContext(), "State of the MainActivity2_A1_MT18070 changed from " + prev + " to Pause",
                Toast.LENGTH_SHORT).show();
        prev = "Pause";
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "State of the MainActivity2_A1_MT18070 changed from " + prev + " Stop");
        Toast.makeText(getApplicationContext(), "State of the MainActivity2_A1_MT18070 changed from " + prev + " to Stop",
                Toast.LENGTH_SHORT).show();
        prev = "Stop";
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "State of the MainActivity2_A1_MT18070 changed from " + prev + " Restart");
        Toast.makeText(getApplicationContext(), "State of the MainActivity2_A1_MT18070 changed from " + prev + " to Restart",
                Toast.LENGTH_SHORT).show();
        prev = "Restart";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "State of the MainActivity2_A1_MT18070 changed from " + prev + " Destroy");
        Toast.makeText(getApplicationContext(), "State of the MainActivity2_A1_MT18070 changed from " + prev + " to Destroy",
                Toast.LENGTH_SHORT).show();
        prev = "Destroy";
    }
}
