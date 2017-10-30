package com.findyourplace.mimel.furp.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.findyourplace.mimel.furp.PublishSite;
import com.findyourplace.mimel.furp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mapGoogle;
    MapView mapGoogleView;
    View myView;

    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    public Map() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapGoogleView = (MapView) myView.findViewById(R.id.mapSite);

        if (mapGoogleView != null) {
            mapGoogleView.onCreate(null);
            mapGoogleView.onResume();
            mapGoogleView.getMapAsync(this);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_map, container, false);
        return myView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mapGoogle = googleMap;

        UiSettings uiSettings = mapGoogle.getUiSettings();

        if (ActivityCompat.checkSelfPermission(Map.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapGoogle.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.isZoomControlsEnabled();
        uiSettings.isZoomGesturesEnabled();


        mapGoogle.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapGoogle.clear();
                Marker site = mapGoogle.addMarker(new MarkerOptions()
                        .position(latLng)
                        .draggable(true));
                double latitud = latLng.latitude;
                double longitud = latLng.longitude;
                ((PublishSite) getActivity()).setLocationLatLng(longitud, latitud);


            }
        });


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Marker site = mapGoogle.addMarker(new MarkerOptions()
                                            .position(marker.getPosition())
                                            .draggable(true));
        return false;
    }
}
