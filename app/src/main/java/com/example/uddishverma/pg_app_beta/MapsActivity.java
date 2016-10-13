package com.example.uddishverma.pg_app_beta;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Double latitude;
    Double longitude;
    String pgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        latitude = b.getDouble("latitude");
        longitude = b.getDouble("longitude");
        pgName = b.getString("pgname");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pgAddress = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(pgAddress).title(pgName).snippet("Welcome To Delhi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pgAddress));
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setCompassEnabled(!mMap.getUiSettings().isCompassEnabled());

//        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(
//                new CameraPosition(new LatLng(latitude, longitude), 15, 2, 5));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pgAddress, 15));

//        mMap.animateCamera(cu);
    }
}
