package com.example.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.content.pm.PackageManager;
=======
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
<<<<<<< HEAD

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

=======
import androidx.annotation.NonNull;
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
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
<<<<<<< HEAD

=======
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
import java.util.ArrayList;

public class BackService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;//to turn requests on and off
<<<<<<< HEAD
    public static final int UPDATE_INTERVAL = 1000; // 5 secs
    public static final int FASTEST_UPDATE_INTERVAL = 1000;
=======
    public static final int UPDATE_INTERVAL = 5000; // 5 secs
    public static final int FASTEST_UPDATE_INTERVAL = 5000;
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
    private LocationRequest locationRequest;
    private LocationManager locationManager;
    public static ArrayList<Person> gfg = new ArrayList<Person>();

    @Override
    public void onCreate() {
<<<<<<< HEAD
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
=======
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
        //----------------start location services--
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
<<<<<<< HEAD
        googleApiClient.connect();

=======


>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
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

<<<<<<< HEAD

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


=======
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
    @Override
    //@SuppressWarnings("deprecated")
    public void onConnected(Bundle dataBundle)
    {
<<<<<<< HEAD
        Log.d("tag", "onconnect is called");

        try {

            createLocationRequest();
=======

        try {
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Locations");
                String identifier = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

                Person x = new Person(identifier, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getTime()); //subject to change as for RDB as key identifier is the PERSON name

                myRef.child(String.valueOf(x.getIdentifier())).setValue(x);    //write to database

<<<<<<< HEAD
                Log.d("tag", String.valueOf(x));
                // Read from the database ---------------------------


            }

=======
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

>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
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

<<<<<<< HEAD
                Log.d("tag", "onLocation changed!!!!!");

=======
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                Log.d("tag", "onLocation changed!!!!!");
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
                if (googleApiClient != null)
                    if (googleApiClient.isConnected() || googleApiClient.isConnecting()){
                        googleApiClient.disconnect();
                        googleApiClient.connect();
                    } else if (!googleApiClient.isConnected()){
                        googleApiClient.connect();
                    }

<<<<<<< HEAD
=======
            }
        }.start();
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275


    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
<<<<<<< HEAD
        googleApiClient.disconnect();
        super.onDestroy();

=======
        super.onDestroy();
        googleApiClient.disconnect();
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275

    }



}