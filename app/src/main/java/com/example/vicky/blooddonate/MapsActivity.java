package com.example.vicky.blooddonate;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMap myMap;
    Geocoder geocoder;
    LocationManager locationManager;
    LocationListener locationListener;
    List<Address> listAddress;
    String place;

    public void searchPlace(View view)
    {
        EditText searchtext = (EditText)findViewById(R.id.stext);
        String maplocation = searchtext.getText().toString();


        if(maplocation !=null || maplocation.equals(""))
        {
            try
            {
                listAddress = geocoder.getFromLocationName(maplocation,1);
                Address address = listAddress.get(0);
                LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                myMap.clear();
                myMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Please enter a valid location for Search",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this);
        place = getIntent().getExtras().getString("Place");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myMap = googleMap;

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng userslocation = new LatLng(location.getLatitude(), location.getLongitude());
                myMap.clear();
                myMap.addMarker(new MarkerOptions().position(userslocation).title("Your Location"));
                myMap.moveCamera(CameraUpdateFactory.newLatLng(userslocation));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
        try {
            listAddress = geocoder.getFromLocationName(place,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address1 = listAddress.get(0);
        LatLng latLng1 = new LatLng(address1.getLatitude(),address1.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng1).title("Donors Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.setMyLocationEnabled(true);
    }
}
