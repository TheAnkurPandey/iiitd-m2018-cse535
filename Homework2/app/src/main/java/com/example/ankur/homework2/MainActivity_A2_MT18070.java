package com.example.ankur.homework2;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity_A2_MT18070 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__a2__mt18070);

        // Begin the transaction.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Replace the contents of the container with the new fragment.
        fragmentTransaction.replace(R.id.topFragment, new TopFragment());
        fragmentTransaction.replace(R.id.bottomFragment, new BottomFragment());

        // Commit the changes added above.
        fragmentTransaction.commit();
    }
}
