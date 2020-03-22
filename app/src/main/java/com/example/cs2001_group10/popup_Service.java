package com.example.cs2001_group10;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class popup_Service extends Service {

    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //This is the service
        //This will run until stopped

        Toast.makeText(this, "Started", Toast.LENGTH_LONG).show();
        //Intent i=getPackageManager().getLaunchIntentForPackage("com.example.cs2001_group10");
        // ^ Opens app if needed.
        Intent popup = new Intent(getBaseContext(), MainActivity.class);
        startActivity(popup);

        return START_STICKY;
    }


    public void onDestroy(){ super.onDestroy();} // Call onDestroy to stop service
}
