package com.example.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LocationHistory extends ListActivity {

    public static final String ROW_ID = "row_id"; // Intent extra key
    private ListView locationListView; // the ListActivity's ListView
    private CursorAdapter locationAdapter; // adapter for ListView

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TS: 1. create list view and set it event handler
        locationListView = getListView(); // Get the activity's list view widget
        //locationListView.setOnItemClickListener(viewLocationListener);

        //TS: 2. create a cursor for the list view
        // map each location name to a TextView in the ListView layout
        String[] from = new String[]{"identifier","latitude", "longitude", "speed", "time"};
        int[] to = new int[]{R.id.idTextView, R.id.latitudeTextView, R.id.longitudeTextView, R.id.speedTextView, R.id.timeTextView};

        locationAdapter = new SimpleCursorAdapter(
                LocationHistory.this, R.layout.location_list_item, null, from, to, 0); //ts: code update to include flag

        //TS: 3. link the cursor to the list view
        setListAdapter(locationAdapter);

    }


    private void display() {
        LocalDatabaseConnector databaseConnector = new LocalDatabaseConnector(LocationHistory.this);
        databaseConnector.open();
        Cursor result = databaseConnector.getAllLocations();
        locationAdapter.changeCursor(result);
        databaseConnector.close();
    }

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
                new Intent(this, MapsActivity.class);
        startActivity(viewLocHistory); // start the AddEditContact Activity
        return super.onOptionsItemSelected(item); // call super's method
    }


    @Override
    protected void onResume() {
        super.onResume(); // call super's onResume method
      /*GetContactsTask(); //TS: this does not work in OnCreate


      //--U21: just for testing the getAllContactsInAListOfStrings() function
      DatabaseConnector dc = new DatabaseConnector(MainActivity.this);
      ArrayList<String> rst = dc.getAllContactsInAListOfStrings();

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("getAllContactsInAListOfStrings()");
      builder.setMessage(rst.toString());
      builder.setPositiveButton("Ok, thanks!", null);
      builder.show(); // display the Dialog */

        display();

    }

    @Override
    protected void onStop() {
        Cursor cursor = locationAdapter.getCursor(); // get current Cursor
        locationAdapter.changeCursor(null); // adapted now has no Cursor

        if (cursor != null) //ts: code update from cursor.deactivate()
            cursor.close(); // release the Cursor's resources (like file handlers and avoid memory leak)

        super.onStop();
    }

    // performs database query outside GUI thread
    private void GetLocationsTask() {
        LocalDatabaseConnector databaseConnector =
                new LocalDatabaseConnector(LocationHistory.this);

        databaseConnector.open();
        // get a cursor containing call contacts
        Cursor result = databaseConnector.getAllLocations();
        locationAdapter.changeCursor(result); // set the adapter's Cursor
        databaseConnector.close();
    }


    /*
    // event listener that responds to the user touching a contact's name
    // in the ListView
    OnItemClickListener viewContactListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
            // create an Intent to launch the ViewContact Activity
            Intent viewContact =
                    new Intent(MainActivity.this, ViewActivity.class);

            // pass the selected contact's row ID as an extra with the Intent
            viewContact.putExtra(ROW_ID, id);
            startActivity(viewContact); // start the ViewContact Activity
        } // end method onItemClick
    }; */
}
