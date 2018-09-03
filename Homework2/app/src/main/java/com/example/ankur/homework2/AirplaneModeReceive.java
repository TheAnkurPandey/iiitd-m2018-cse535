package com.example.ankur.homework2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AirplaneModeReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
        if(isAirplaneModeOn){
            Log.i("Output", "Airplane mode is on");
            context.startService(new Intent(context, MusicService_A2_MT18070.class));
        } else {
            Log.i("Output", "Airplane mode is off");
            context.stopService(new Intent(context, MusicService_A2_MT18070.class));
        }
    }
}
