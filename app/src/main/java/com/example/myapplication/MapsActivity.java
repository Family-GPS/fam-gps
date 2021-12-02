package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

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
        Intent serviceIntent = new Intent(this, BackService.class);
        startService(serviceIntent);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Locations");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("tag", "enter Data SHCUCK");
                mMap.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    mMap.addMarker(new MarkerOptions().position(new LatLng(ds.getValue(Person.class).getLatitude(),  ds.getValue(Person.class).getLongitude()))).setTitle(
                            "Name: "+ ds.getValue(Person.class).getIdentifier() +"\n Time:"+  sdf.format(new Date((long)  ds.getValue(Person.class).getTime()))  +
                                    "\nLongi=" +  ds.getValue(Person.class).getLongitude() + "\nLatitude=" +  ds.getValue(Person.class).getLatitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng( ds.getValue(Person.class).getLatitude(),  ds.getValue(Person.class).getLongitude())));

                    Toast.makeText(getApplicationContext(), "Locations updated!", Toast.LENGTH_LONG).show();
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

    // create the Activity's menu from a menu resource XML file
    //for locations history display from local db
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // handle choice from options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // create a new Intent to launch the AddEditContact Activity
        Intent viewLocHistory =
                new Intent(MapsActivity.this, LocationHistory.class);
        startActivity(viewLocHistory); // start the AddEditContact Activity
        return super.onOptionsItemSelected(item); // call super's method
    }

}


