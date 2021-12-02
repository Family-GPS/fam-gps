package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
//import android.location.LocationRequest;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.LocationServices;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //


       /*Bundle extras = getIntent().getExtras();

        ArrayList<Person> updates = new ArrayList<Person>();

        if (extras != null){
            updates = (ArrayList<Person>) getIntent().getSerializableExtra("all");
            Draw(updates);
        }*/



    }



        @Override
        public void onMapReady(GoogleMap googleMap) {
        /*if (BackService.gfg != null) {

            for (Person l : BackService.gfg) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                        l.getLatitude(),
                                        l.getLongitude()
                                )
                        )
                )
                        .setTitle("Name= " + l.getIdentifier() + "\n Longi=" + l.getLongitude() + "\n Latitude=" + l.getLatitude() +
                                "\n Time=" + l.getTime()
                        );
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new
                                LatLng(l.getLatitude(), l.getLongitude()
                        )
                        )
                );
            }
        }*/

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater()
                            .inflate(R.layout.marker_layout,
                                    null);
                    v.setLayoutParams(new RelativeLayout.LayoutParams(800,
                            RelativeLayout.LayoutParams.WRAP_CONTENT)
                    );
                    TextView Longi = v.findViewById(R.id.longitudeTextView);
                    Longi.setText(marker.getTitle()
                    );
                    return v;
                }
            });
        }


    @Override
    public void onClick(View view) {

    }






    public void Draw(ArrayList<Person> all){
        for (Person p: all)
        {
            Log.d("hello", String.valueOf(p.getIdentifier()));
            mMap.addMarker(new MarkerOptions().position(new LatLng(
                    p.getLatitude(), p.getLongitude()))).setTitle(
                    p.getSpeed()+
                            ":\n Name="+p.getIdentifier()+
                            ": \nLongi="+ p.getLongitude()+
                            "\nLatitude="+ p.getLatitude()+
                            "\n Speed:"+ p.getSpeed());

            //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(p.getLatitude(), p.getLongitude())));

        }

    }

    @Override
    protected void onStart() { //called before on resume – corresponds to onStop
        super.onStart();

        //in a separate thread
        //onConnected is then called
    }
    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }



/* if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Locations");
                String identifier = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

                Person x = new Person(identifier, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getTime()); //subject to change as for RDB as key identifier is the PERSON name

                myRef.child(String.valueOf(x.getIdentifier())).setValue(x);

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

                                for (Person p: gfg)
                                {
                                    Log.d("hello", String.valueOf(p.getIdentifier()));
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(
                                            p.getLatitude(), p.getLongitude()))).setTitle(
                                            p.getSpeed()+
                                                    ":\n Name="+p.getIdentifier()+
                                                    ": \nLongi="+ p.getLongitude()+
                                                    "\nLatitude="+ p.getLatitude()+
                                                    "\n Speed:"+ p.getSpeed());

                                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(p.getLatitude(), p.getLongitude())));

                                }
                            }
                        }.start();




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });
                */



            //remove location updates so that it resets




    }






/*
package com.example.myapplication;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import com.example.myapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;  THIS DOESNT WORK!
//import android.support.v4.content.ContextCompat;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements View.OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback, LocationListener
{

    private GoogleApiClient googleApiClient;//to turn requests on and off
    private TextView textLat;
    private TextView textLong;
    public static final int UPDATE_INTERVAL = 5000;   // 5 secs
    public static final int FASTEST_UPDATE_INTERVAL = 5000;
    private LocationRequest locationRequest;
    private ToggleButton trackButton;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL);



    }
    @Override
    //@SuppressWarnings("deprecated")
    public void onConnected(Bundle dataBundle)
    {
        // Put code to run after connecting here ex. register to receive location updates

        // Check Permissions Now
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
               // textLat.setText(location.getLatitude()+"");
                //textLong.setText(location.getLongitude()+"");
                Log.d("tag", "Location not null");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Locations");
                String identifier = Settings.Secure.getString(getContentResolver(), "bluetooth_name");
                Log.d("tag", String.valueOf(location.getTime()));
                Person x = new Person(identifier, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getTime()); //subject to change as for RDB as key identifier is the PERSON name

                myRef.child(String.valueOf(x.getIdentifier())).setValue(x);
                Log.d("rawan", "" +location.getLatitude()+ "   " + location.getLongitude());


                // Read from the database ---------------------------

                Person[] locs = new Person[5];

                ArrayList<Person> gfg = new ArrayList<Person>();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i=0;
                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            gfg.add(ds.getValue(Person.class));
                        }


                        Log.d("tag", String.valueOf(gfg.get(0).getTime()));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(
                                gfg.get(0).getLatitude(), gfg.get(0).getLongitude()))).setTitle(
                                gfg.get(0).getSpeed()+
                                        ": \nLongi="+ gfg.get(0).getLongitude()+
                                        "\nLatitude="+ gfg.get(0).getLatitude()+
                                        "\n Speed:"+ gfg.get(0).getSpeed());

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gfg.get(0).getLatitude(), gfg.get(0).getLongitude())));

                        Log.d("tag", "" +location.getLatitude()+ "   " + location.getLongitude());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        }
        catch (SecurityException s){
            Log.d("rawan","Not able to run location services...");
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123)
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                onConnected(new Bundle());
    }
    @Override
    public void onConnectionSuspended(int i) {}


    @Override
    public void onLocationChanged(Location location)
    {
        //textLat.setText(location.getLatitude()+"");
        //textLong.setText(location.getLongitude()+"");
        Log.d("rawan", "" +location.getLatitude()+ "    " + location.getLongitude());
    }

    @Override
    protected void onStart() { //called before on resume – corresponds to onStop
        super.onStart();
        googleApiClient.connect(); //connect to google play services not GPS!
        //in a separate thread
        //onConnected is then called
    }
    @Override
    protected void onPause() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(
            ConnectionResult connectionResult) {
        // Put code to run if connection fails here
        // ex. print out an error message !
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker){
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.marker_layout, null);
                v.setLayoutParams(new RelativeLayout.LayoutParams(800, RelativeLayout.LayoutParams.WRAP_CONTENT));
                TextView Longi = v.findViewById(R.id.longitudeTextView);
                Longi.setText(marker.getTitle());
                return v;
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}*/

