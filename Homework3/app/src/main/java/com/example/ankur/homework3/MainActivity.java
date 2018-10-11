package com.example.ankur.homework3;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Request user permissions in runtime */
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                100);
        /* Request user permissions in runtime */

        // Begin the transaction.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Replace the contents of the container with the new fragment.
        fragmentTransaction.add(R.id.ma_fl, new ListFragment());

        // Commit the changes added above.
        fragmentTransaction.commit();
    }

    /*public void onClear(View view) {
        RadioGroup radioGroup = view.findViewById(R.id.df_rg);
        radioGroup.clearCheck();
    }*/

}
