package com.example.myapplication;

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> a5d1810aedbc7f43df04106a5c6e658c237295c2
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
<<<<<<< HEAD
=======

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

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
    }



    //@SuppressWarnings("deprecated")
    public void onR() {
    }

            //change the time of location updates



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.d("MAP",String.valueOf(BackService.gfg));
       /* if (!BackService.gfg.isEmpty()) {
            Log.d("tag", "NOT EMPTYYYYYYYYYYYY");
            for (Person l : BackService.gfg) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(
                        l.getLatitude(), l.getLongitude()))).setTitle("Longi=" + l.getLongitude() + "\nLatitude=" + l.getLatitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(l.getLatitude(), l.getLongitude())));
            }
        }*/


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Locations");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Person> gfg = new ArrayList<Person>();

                Log.d("tag", "enter Data SHCUCK");
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(
                      //      ds.getValue()getLatitude(), l.getLongitude()))).setTitle("Longi=" + l.getLongitude() + "\nLatitude=" + l.getLatitude());
                    gfg.add(ds.getValue(Person.class));
                   // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(l.getLatitude(), l.getLongitude())));
                }

                for (Person l: gfg)
                {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(l.getLatitude(), l.getLongitude()))).setTitle(
                            "Name: "+l.getIdentifier() +"\n Time:"+l.getTime()+
                                    "\nLongi=" + l.getLongitude() + "\nLatitude=" + l.getLatitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(l.getLatitude(), l.getLongitude())));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });


        mMap.setOnMapLongClickListener(this);

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
    public void onMapLongClick(LatLng latLng) {
        Location NewLocationMarker = new Location("new");


        mMap.addMarker(new MarkerOptions().position(latLng)).setTitle("Longi="+latLng.longitude + "\nLatitude="+latLng.latitude);
    }
}




/***********package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
>>>>>>> a2fa8fd6d7faf6546e865999922fa1719871d8ee
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
=======
import android.content.pm.PackageManager;
>>>>>>> a2fa8fd6d7faf6546e865999922fa1719871d8ee
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
>>>>>>> a5d1810aedbc7f43df04106a5c6e658c237295c2
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
>>>>>>> a2fa8fd6d7faf6546e865999922fa1719871d8ee
>>>>>>> a5d1810aedbc7f43df04106a5c6e658c237295c2
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD

=======
<<<<<<< HEAD

=======
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //
<<<<<<< HEAD


       /*Bundle extras = getIntent().getExtras();

        ArrayList<Person> updates = new ArrayList<Person>();

        if (extras != null){
            updates = (ArrayList<Person>) getIntent().getSerializableExtra("all");
            Draw(updates);
        }*/
=======


       /*Bundle extras = getIntent().getExtras();

        ArrayList<Person> updates = new ArrayList<Person>();

        if (extras != null){
            updates = (ArrayList<Person>) getIntent().getSerializableExtra("all");
            Draw(updates);
        }*/

>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275


/**
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
<<<<<<< HEAD
        }*/ /***
=======
        }*/
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275

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



<<<<<<< HEAD


=======



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
>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275

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
<<<<<<< HEAD

        super.onStop();
    }



=======

        super.onStop();
    }



>>>>>>> be0e90ddbd09e47907816c231b8e704de5867275
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




   // }


/*----------------*/



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
>>>>>>> a2fa8fd6d7faf6546e865999922fa1719871d8ee
>>>>>>> a5d1810aedbc7f43df04106a5c6e658c237295c2
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Locations");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Person> gfg = new ArrayList<Person>();

                Log.d("tag", "enter Data SHCUCK");
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    gfg.add(ds.getValue(Person.class));
                }


                for (Person l: gfg)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    mMap.addMarker(new MarkerOptions().position(new LatLng(l.getLatitude(), l.getLongitude()))).setTitle(
                            "Name: "+l.getIdentifier() +"\n Time:"+  sdf.format(new Date((long) l.getTime()))  +
                                    "\nLongi=" + l.getLongitude() + "\nLatitude=" + l.getLatitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(l.getLatitude(), l.getLongitude())));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
<<<<<<< HEAD

        });
=======
>>>>>>> a5d1810aedbc7f43df04106a5c6e658c237295c2

        });


<<<<<<< HEAD
=======

>>>>>>> a5d1810aedbc7f43df04106a5c6e658c237295c2
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
    public void onMapLongClick(LatLng latLng) {
        Location NewLocationMarker = new Location("new");
<<<<<<< HEAD


=======


>>>>>>> a5d1810aedbc7f43df04106a5c6e658c237295c2
        mMap.addMarker(new MarkerOptions().position(latLng)).setTitle("Longi="+latLng.longitude + "\nLatitude="+latLng.latitude);
    }
}


