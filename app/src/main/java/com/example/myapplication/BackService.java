package com.example.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BackService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;//to turn requests on and off
    public static final int UPDATE_INTERVAL = 1000; // 5 secs
    public static final int FASTEST_UPDATE_INTERVAL = 1000;
    private LocationRequest locationRequest;
    private LocationManager locationManager;


    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //----------------start location services--
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL);

        Log.d("tag", "Location service onCreate is called");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    protected void createLocationRequest() {
        //remove location updates so that it resets
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this); //Import should not be **android.Location.LocationListener**
        //import should be **import com.google.android.gms.location.LocationListener**;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //restart location updates with the new interval
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }


    @Override
    //@SuppressWarnings("deprecated")
    public void onConnected(Bundle dataBundle)
    {
        Log.d("tag", "onconnect is called");

        try {

            createLocationRequest();
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            Log.d("tag", String.valueOf(location));
            if (location != null) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Locations");
                String identifier = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String dateTim = sdf.format(new Date(l.getTime()));*/
                Person x = new Person(identifier, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getTime()); //subject to change as for RDB as key identifier is the PERSON name

                myRef.child(String.valueOf(x.getIdentifier())).setValue(x);    //write to database

                Log.d("tag", String.valueOf(x));
                // Read from the database ---------------------------


            }

            //change the time of location updates
            locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000)
                    .setFastestInterval(5000);

            //restart location updates with the new interval
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        }
        catch (SecurityException s){
            Log.d("rawan","Not able to run location services...");
        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

                Log.d("tag", "onLocation changed!!!!!");

                if (googleApiClient != null)
                    if (googleApiClient.isConnected() || googleApiClient.isConnecting()){
                        googleApiClient.disconnect();
                        googleApiClient.connect();
                    } else if (!googleApiClient.isConnected()){
                        googleApiClient.connect();
                    }



    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        googleApiClient.disconnect();
        super.onDestroy();


    }



}