package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.ArrayList;
public class LocalDatabaseConnector {

    // database name
    private static final String DATABASE_NAME = "Locations";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    // public constructor for DatabaseConnector
    public LocalDatabaseConnector(Context context) {
        databaseOpenHelper =
                new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    // open the database connection
    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    // close the database connection
    public void close() {
        if (database != null)
            database.close();
    }

    // inserts a new location in the database
    public void insertLocation(String identifier, double latitude, double longitude, double speed,
                               double time) {
        ContentValues newLocation = new ContentValues();
        newLocation.put("identifier", identifier);
        newLocation.put("latitude", latitude);
        newLocation.put("longitude", longitude);
        newLocation.put("speed", speed);
        newLocation.put("time", time);

        open(); // open the database
        database.insert("locations", null, newLocation);
        close(); // close the database
    }

    public Cursor getAllLocations() {
        return database.query("locations", new String[]{"_id", "identifier", "latitude", "longitude", "speed", "time"},
                null, null, null, null, null/*order by*/);
    }

    // get a Cursor containing all information about the location specified by the given id
    public Cursor getOneLocation(long id) {

        return database.query("locations", null/*get all fields*/,
                "_id=" + id /*selection*/, null, null, null, null);

    }

    public ArrayList<String> getAllLocationsInAListOfStrings()
    {
        String rst = "";
        ArrayList<String> listOfLocations = new ArrayList<String>();

        open();
        Cursor c = database.query("locations", null,null, null, null, null, "name"/*order by*/);

        if (c.moveToFirst()){
            do{

                rst += c.getString(1)+"\n";//identifier
                rst += c.getString(2)+"\n";//latitude
                rst += c.getString(3)+"\n";//longitude
                rst += c.getString(4)+"\n";//speed
                rst += c.getString(5)+"\n";//time
                rst += "--------------------\n";
                listOfLocations.add(rst);
                rst = "";

            }while(c.moveToNext());
        }

        close();
        return listOfLocations;
    }

    //inner class:
    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // creates the locations table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            // query to create a new table named locations
            String createQuery = "CREATE TABLE locations" +
                    "(_id integer primary key autoincrement," +
                    "identifier TEXT, latitude DOUBLE, longitude DOUBLE, speed DOUBLE, time DOUBLE);";

            db.execSQL(createQuery); // execute the query
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
        }
    }
}
