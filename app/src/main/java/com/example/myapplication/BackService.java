package com.example.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.NonNull;
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
import java.util.ArrayList;

public class BackService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;//to turn requests on and off
    public static final int UPDATE_INTERVAL = 5000; // 5 secs
    public static final int FASTEST_UPDATE_INTERVAL = 5000;
    private LocationRequest locationRequest;
    private LocationManager locationManager;
    public static ArrayList<Person> gfg = new ArrayList<Person>();

    @Override
    public void onCreate() {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //----------------start location services--
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


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

    @Override
    //@SuppressWarnings("deprecated")
    public void onConnected(Bundle dataBundle)
    {

        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Locations");
                String identifier = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

                Person x = new Person(identifier, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getTime()); //subject to change as for RDB as key identifier is the PERSON name

                myRef.child(String.valueOf(x.getIdentifier())).setValue(x);    //write to database

                // Read from the database ---------------------------


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        new CountDownTimer(30000, 30000) {

                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                for (DataSnapshot ds: dataSnapshot.getChildren())
                                {
                                    gfg.add(ds.getValue(Person.class));
                                }




                            }
                        }.start();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });
            }


            //remove location updates so that it resets
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

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

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                Log.d("tag", "onLocation changed!!!!!");
                if (googleApiClient != null)
                    if (googleApiClient.isConnected() || googleApiClient.isConnecting()){
                        googleApiClient.disconnect();
                        googleApiClient.connect();
                    } else if (!googleApiClient.isConnected()){
                        googleApiClient.connect();
                    }

            }
        }.start();


    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();

    }



}