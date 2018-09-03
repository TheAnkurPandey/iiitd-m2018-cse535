package com.example.ankur.homework2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent newIntent = new Intent(context, MainActivity_A2_MT18070.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i("Output", "BOOT_COMPLTED recieved");
            context.startService(new Intent(context, MusicService_A2_MT18070.class));
            context.startActivity(newIntent);
        }
    }
}

