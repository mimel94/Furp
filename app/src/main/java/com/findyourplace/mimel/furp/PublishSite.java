package com.findyourplace.mimel.furp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.findyourplace.mimel.furp.fragment.Map;
import com.findyourplace.mimel.furp.models.FirebaseReferences;
import com.findyourplace.mimel.furp.models.Site;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PublishSite extends AppCompatActivity {
    Button create;
    EditText name;
    DatabaseReference refSite;
    String latitud;
    String longitud;
    EditText city;
    EditText description;
    EditText type;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    FragmentManager myFragment = getSupportFragmentManager();
    ImageButton photoSiteButon;
    private static final int GALERY_INTENT = 1;
    private ProgressDialog myProgressDialog;
    StorageReference myStorage;
    Uri descargarFoto;
    Site place;

    @Override
    public void onStart() {
        super.onStart();
       // mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            //mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_site);
        create = (Button)findViewById(R.id.btnCreate);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText)findViewById(R.id.txtName);
        //location = (EditText)findViewById(R.id.txtLocation);
        city = (EditText)findViewById(R.id.txtCity);
        description = (EditText)findViewById(R.id.txtDescription);
        type = (EditText)findViewById(R.id.txtType);
        database = FirebaseDatabase.getInstance();
        refSite = database.getReference(FirebaseReferences.SITE_REFERENCE);
        myFragment.beginTransaction().replace(R.id.mapView, new Map()).commit();

        myStorage = FirebaseStorage.getInstance().getReference();
        myProgressDialog = new ProgressDialog(this);
        photoSiteButon = (ImageButton)findViewById(R.id.btn_img_site);

        place = new Site();
        photoSiteButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALERY_INTENT);
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place.setName(name.getText().toString());
                place.setLocation(latitud, longitud);
                place.setCity(city.getText().toString());
                place.setDescription(description.getText().toString());
                place.setType(type.getText().toString());
                refSite.child(mAuth.getCurrentUser().getUid()).push().setValue(place);
                Toast.makeText(PublishSite.this, "Sitio creado!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PublishSite.this, NavigationPanel.class));
                finish();
            }
        });

    }


    public void setLocationLatLng(double longitud, double latitud) {
        this.latitud =  String.valueOf(latitud);
        this.longitud = String.valueOf(longitud);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALERY_INTENT && resultCode == RESULT_OK){
            myProgressDialog.setTitle("subiendo...");
            myProgressDialog.setMessage("Subiendo foto de perfil");
            myProgressDialog.setCancelable(false);
            myProgressDialog.show();

            Uri urlPhoto = data.getData();
            //ojo la img se sube al servidor y primero deberia mostrarse, luego si se sube !!arreglarlo!!
            StorageReference filePath = myStorage.child("fotos/"+mAuth.getCurrentUser().getUid()+"/site/"+urlPhoto.
                    getLastPathSegment());

            filePath.putFile(urlPhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myProgressDialog.dismiss();

                    descargarFoto= taskSnapshot.getDownloadUrl();
                    Glide.with(PublishSite.this).load(descargarFoto).fitCenter()
                            .centerCrop().into(photoSiteButon);

                    Toast.makeText(PublishSite.this, "Se subio correctamente la foto", Toast.LENGTH_SHORT).show();
                    place.setProfilePhotoUrl(descargarFoto.toString());

                }
            });
        }
    }
}
