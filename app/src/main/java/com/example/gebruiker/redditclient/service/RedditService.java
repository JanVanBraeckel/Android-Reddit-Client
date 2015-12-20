package com.example.gebruiker.redditclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RedditService extends Service {
    public RedditService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub""
        Log.d("DB", "Service Started");
        Toast.makeText(getBaseContext(), "Service Started", Toast.LENGTH_LONG)
                .show();

//        new DoBackgroundTask()
//                .execute(new String[] {
//                        "http://192.168.2.7/orca_android_app/select_country.php?key=country",
//                        "country","id", "name" });
        stopSelf();
        // return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
}
