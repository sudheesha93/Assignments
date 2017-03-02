package com.example.sudheesha.smartwear;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    double lat, lng;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        createLocationRequest();
        // make a buidler for GoogleApiClient //
        if(mGoogleApiClient==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void createLocationRequest(){

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED ) {

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(20000); // 20 seconds
            mLocationRequest.setFastestInterval(10000); //10 seconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("You are Here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    // must declare methods //

    public void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
        if(mGoogleApiClient.isConnected()){
            startLocationUpdates();
        }
    }
    public void onStop(){
        mGoogleApiClient.disconnect();
        stopLocationUpdate();
        super.onStop();
    }
    public void onPause(){
        mGoogleApiClient.disconnect();
        stopLocationUpdate();
        super.onPause();

    }
    public void onResume(){
        mGoogleApiClient.connect();
        super.onResume();
        if(mGoogleApiClient.isConnected()){
            startLocationUpdates();
        }
    }

    protected void stopLocationUpdate(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    // Get Location //
    public void onLocationChanged(Location location){
        if(location!=null){
            lat = location.getLatitude();
            lng = location.getLongitude();
            mapFragment.getMapAsync(this);
        }
    }

    public void onConnectionSuspended(int arg0){

    }
    public void onStatusChange(String provider, int status, Bundle extras){

    }

    // Must Declare Callback Methods //
    public void onConnected(Bundle args0){

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED ) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mLocation!=null){
                lat=mLocation.getLatitude();
                lng = mLocation.getLatitude();
                mapFragment.getMapAsync(this);
            }
            if(mGoogleApiClient.isConnected()){
                startLocationUpdates();
            }
        }
    }
    public void onConnectionFailed(ConnectionResult result){
    }
}