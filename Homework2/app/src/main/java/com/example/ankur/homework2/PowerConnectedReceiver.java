package com.example.ankur.homework2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerConnectedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
            Log.i("Output", "ACTION_POWER_CONNECTED recieved");
            context.startService(new Intent(context, MusicService_A2_MT18070.class));
        }
        else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Log.i("Output", "ACTION_POWER_DISCONNECTED recieved");
            context.stopService(new Intent(context, MusicService_A2_MT18070.class));
        }
    }
}
