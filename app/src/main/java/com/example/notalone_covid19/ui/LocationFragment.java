package com.example.notalone_covid19.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notalone_covid19.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class LocationFragment extends Fragment implements OnMapReadyCallback
{

    View view;

    public LocationFragment(Context cntx)
    {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapLocation);
        mapFragment.getMapAsync(this);

    }



    //open map with scores , name and rating form SP
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // Add a marker in Sydney and move the camera
        MapsInitializer.initialize(getContext());



//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, googleMap.getMaxZoomLevel() / 2));

    }
}