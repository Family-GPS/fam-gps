package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
//import android.location.LocationRequest;
import android.os.Bundle;
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
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.LocationServices;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener, LocationListener, View.OnClickListener {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private GoogleApiClient googleApiClient;//to turn requests on and off
    public static final int UPDATE_INTERVAL = 5000; // 5 secs
    public static final int FASTEST_UPDATE_INTERVAL = 2000;
    private LocationRequest locationRequest;

  //  Person[] persons;

    Person[] locs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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



        //WRITING TO FIREBASE ------------

    }

        /* Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); ****/

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
    public void onClick(View view) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Check Permissions Now
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                //WRITING TO FIREBASE
                Log.d("loc", "Location not null");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Locations");
                String identifier = Settings.Secure.getString(getContentResolver(), "bluetooth_name");
                Log.d("Trying", String.valueOf(location.getTime()));
                Person x = new Person(identifier, location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getTime()); //subject to change as for RDB as key identifier is the PERSON name

                myRef.child(String.valueOf(x.getIdentifier())).setValue(x);

                // Read from the database ---------------------------

                locs = new Person[5];

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i=0;
                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            locs[i++] = ds.getValue(Person.class);
                        }


                        Log.d("halo", String.valueOf(locs[0].getTime()));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(
                                locs[0].getLatitude(), locs[0].getLongitude()))).setTitle(
                                locs[0].getSpeed()+
                                        ": \nLongi="+locs[0].getLongitude()+
                                        "\nLatitude="+locs[0].getLatitude()+
                                        "\n Speed:"+ locs[0].getSpeed());

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(locs[0].getLatitude(), locs[0].getLongitude())));

                Log.d("TS", "" +location.getLatitude()+ "   " + location.getLongitude());
            }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}

                });
            }


            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        }
        catch (SecurityException s){
            Log.d("TS","Not able to run location services...");
        }
    }





    @Override
    public void onConnectionSuspended(int i) {

    }


    }


