package com.example.notalone_covid19;

import android.os.Bundle;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MySharedPreferences msp;
    private ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        msp = new MySharedPreferences(this);
        lstView = findViewById(R.id.listPlaces);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showtopTenList();
        // Add a marker in Sydney and move the camera

    }

    private void showtopTenList() {

    }
}
