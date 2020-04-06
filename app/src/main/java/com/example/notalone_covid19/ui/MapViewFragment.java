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
import com.example.notalone_covid19.RiskGroupPerson;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MapViewFragment extends Fragment implements OnMapReadyCallback
{
    private View view;
    private List<RiskGroupPerson> people = new ArrayList<>();
    private GoogleMap googleMap = null;

    public MapViewFragment(Context cntx)
    {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void setPeople(List<RiskGroupPerson> people){
        this.people = people;
        if(googleMap != null)
            addPeopleToMap();
    }

    private void addPeopleToMap(){
        for(RiskGroupPerson person : people){
            LatLng latLng = new LatLng(person.getLatitude(),person.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng));
        }
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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    //open map with scores , name and rating form SP
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // Add a marker in Sydney and move the camera
        MapsInitializer.initialize(getContext());
        this.googleMap = googleMap;
        if(people.size() > 0)
            addPeopleToMap();



//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, googleMap.getMaxZoomLevel() / 2));

    }
}