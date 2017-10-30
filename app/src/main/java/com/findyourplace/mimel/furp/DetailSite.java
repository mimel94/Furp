package com.findyourplace.mimel.furp;

import android.*;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findyourplace.mimel.furp.fragment.Map;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailSite extends AppCompatActivity implements OnMapReadyCallback {

    TextView name;
    TextView type;
    TextView description;

    GoogleMap mapGoogle;
    MapView mapGoogleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_site);
        name = (TextView)findViewById(R.id.txt_name_site);
        type = (TextView)findViewById(R.id.txt_type_site);
        description = (TextView)findViewById(R.id.txt_description_site);
        name.setText(getIntent().getStringExtra("name"));
        type.setText(getIntent().getStringExtra("type"));
        description.setText(getIntent().getStringExtra("description"));

        mapGoogleView = (MapView)findViewById(R.id.map_site);
        if (mapGoogleView != null) {
            mapGoogleView.onCreate(null);
            mapGoogleView.onResume();
            mapGoogleView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapGoogle = googleMap;
        UiSettings uiSettings = mapGoogle.getUiSettings();
        uiSettings.isZoomControlsEnabled();
        uiSettings.isZoomGesturesEnabled();

        LatLng mySite = new LatLng(Double.parseDouble(getIntent().getStringExtra("longitud")),
                Double.parseDouble(getIntent().getStringExtra("latitud")));

        mapGoogle.addMarker(new MarkerOptions().
                position( mySite )
                .title(getIntent().getStringExtra("name")));

        mapGoogle.moveCamera(CameraUpdateFactory.newLatLng(mySite));

    }
}
