package com.example.sudheesha.myfirstapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void getPlace(View view)
    {
        EditText loc=(EditText)findViewById(R.id.add);
        String location=loc.getText().toString();
        List<android.location.Address> addList=null;
        if(location !=null || !location.equals(""))
        {
            Geocoder geo=new Geocoder(this);
            try
            {
                addList = geo.getFromLocationName(location, 1);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            android.location.Address add=addList.get(0);
            LatLng latlng=new LatLng(add.getLatitude(), add.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      /*  mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
        }
        mMap.setMyLocationEnabled(true);*/

       LatLng kansas = new LatLng(39.0997,-94.5786);
        mMap.addMarker(new MarkerOptions().position(kansas).title("Marker in Kansas City"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kansas));
    }


}
