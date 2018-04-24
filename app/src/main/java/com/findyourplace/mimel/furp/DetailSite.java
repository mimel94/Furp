package com.findyourplace.mimel.furp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.findyourplace.mimel.furp.fragment.Map;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailSite extends AppCompatActivity implements OnMapReadyCallback {

    TextView name;
    TextView type;
    TextView description;
    ImageView photo;
    Button buttonEdit, buttonDelete;

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
        photo = (ImageView)findViewById(R.id.img_photo_site);
        String url = getIntent().getStringExtra("photo");
        if(!url.isEmpty()){
            StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
            Glide.with(DetailSite.this)
                    .using(new FirebaseImageLoader())
                    .load(httpsReference)
                    .into(photo);
        }


        mapGoogleView = (MapView)findViewById(R.id.map_site);
        if (mapGoogleView != null) {
            mapGoogleView.onCreate(null);
            mapGoogleView.onResume();
            mapGoogleView.getMapAsync(this);
        }

        buttonEdit = (Button)findViewById(R.id.btn_edit);
        buttonDelete = (Button)findViewById(R.id.btn_delete);

        /*buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailSite.this,EditSite.class);
                i.putExtra("idSite", )
            }
        });*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapGoogle = googleMap;
        UiSettings uiSettings = mapGoogle.getUiSettings();
        uiSettings.isZoomControlsEnabled();
        uiSettings.isZoomGesturesEnabled();

        LatLng mySite = new LatLng(Double.parseDouble(getIntent().getStringExtra("latitud")),
                Double.parseDouble(getIntent().getStringExtra("longitud")));

        mapGoogle.addMarker(new MarkerOptions().
                position( mySite )
                .title(getIntent().getStringExtra("name")));

        mapGoogle.moveCamera(CameraUpdateFactory.newLatLng(mySite));

    }
}
