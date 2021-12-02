package com.example.myapplication;

import android.content.Intent;
import android.util.Log;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("tag", "Service started from application class");
        Intent serviceIntent = new Intent(this, BackService.class);
        startService(serviceIntent);

    }


}
