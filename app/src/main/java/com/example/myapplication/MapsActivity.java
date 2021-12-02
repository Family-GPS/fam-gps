package com.example.myapplication;

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
import java.util.Date;

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

        });



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


