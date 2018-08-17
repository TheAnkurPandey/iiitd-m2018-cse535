package com.example.ankur.homework1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_A1_MT18070 extends AppCompatActivity {

    private Button button;
    EditText mEdit;

    private static final String TAG = "Tag";
    private static String prev = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__a1__mt18070);

        if (prev == "") {
            Log.i(TAG, "State of the MainActivity_A1_MT18070 is Create");
            Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 is Create",
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "State of the MainActivity_A1_MT18070 changed from " + prev + " Create");
            Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 changed from " + prev + " to Create",
                    Toast.LENGTH_SHORT).show();
        }
        prev = "Create";

        button = findViewById(R.id.masb);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_A1_MT18070.this, MainActivity2_A1_MT18070.class);

                mEdit  = findViewById(R.id.man);
                intent.putExtra("name", mEdit.getText().toString());

                mEdit  = findViewById(R.id.marn);
                intent.putExtra("rollno", mEdit.getText().toString());

                mEdit  = findViewById(R.id.mab);
                intent.putExtra("branch", mEdit.getText().toString());

                mEdit  = findViewById(R.id.mac1);
                intent.putExtra("course1", mEdit.getText().toString());

                mEdit  = findViewById(R.id.mac2);
                intent.putExtra("course2", mEdit.getText().toString());

                mEdit  = findViewById(R.id.mac3);
                intent.putExtra("course3", mEdit.getText().toString());

                mEdit  = findViewById(R.id.mac4);
                intent.putExtra("course4", mEdit.getText().toString());

                startActivity(intent);
            }
        });

        button = findViewById(R.id.macb);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mEdit  = findViewById(R.id.man);
                mEdit.setText("");

                mEdit  = findViewById(R.id.marn);
                mEdit.setText("");

                mEdit  = findViewById(R.id.mab);
                mEdit.setText("");

                mEdit  = findViewById(R.id.mac1);
                mEdit.setText("");

                mEdit  = findViewById(R.id.mac2);
                mEdit.setText("");

                mEdit  = findViewById(R.id.mac3);
                mEdit.setText("");

                mEdit  = findViewById(R.id.mac4);
                mEdit.setText("");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "State of the MainActivity_A1_MT18070 changed from " + prev + " Start");
        Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 changed from " + prev + " to Start",
                Toast.LENGTH_SHORT).show();
        prev = "Start";
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "State of the MainActivity_A1_MT18070 changed from " + prev + " Resume");
        Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 changed from " + prev + " to Resume",
                Toast.LENGTH_SHORT).show();
        prev = "Resume";
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "State of the MainActivity_A1_MT18070 changed from " + prev + " Pause");
        Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 changed from " + prev + " to Pause",
                Toast.LENGTH_SHORT).show();
        prev = "Pause";
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "State of the MainActivity_A1_MT18070 changed from " + prev + " Stop");
        Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 changed from " + prev + " to Stop",
                Toast.LENGTH_SHORT).show();
        prev = "Stop";
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "State of the MainActivity_A1_MT18070 changed from " + prev + " Restart");
        Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 changed from " + prev + " to Restart",
                Toast.LENGTH_SHORT).show();
        prev = "Restart";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "State of the MainActivity_A1_MT18070 changed from " + prev + " Destroy");
        Toast.makeText(getApplicationContext(), "State of the MainActivity_A1_MT18070 changed from " + prev + " to Destroy",
                Toast.LENGTH_SHORT).show();
        prev = "Destroy";
    }

}
