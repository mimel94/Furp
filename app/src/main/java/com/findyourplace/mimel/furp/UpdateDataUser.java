package com.findyourplace.mimel.furp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.findyourplace.mimel.furp.models.User;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

/**
 * Created by mimel on 12/09/17.
 */

public class UpdateDataUser extends AppCompatActivity {
    Button updateData;
    FirebaseDatabase database;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference refUser;
    EditText name;
    EditText city;
    EditText description;
    EditText email;
    User user = new User();
    private ImageButton uploadPhoto;
    private StorageReference myStorage;
    private static final int GALERY_INTENT = 1;
    private ProgressDialog myProgressDialog;
    Uri descargarFoto;
    StorageReference httpsReference;

    @Override
    public void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            //mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onCreate(Bundle saveInstanBundle){
        super.onCreate(saveInstanBundle);
        setContentView(R.layout.user_data_edit);

        updateData = (Button)findViewById(R.id.btnSave);


        name = (EditText) findViewById(R.id.txtName);
        city = (EditText) findViewById(R.id.txtCity);
        description = (EditText) findViewById(R.id.txtDescription);
        email = (EditText) findViewById(R.id.txtEmail);
        database = FirebaseDatabase.getInstance();
        refUser = database.getReference("user");
        myStorage = FirebaseStorage.getInstance().getReference();
        uploadPhoto = (ImageButton)findViewById(R.id.btn_photo);
        myProgressDialog = new ProgressDialog(this);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALERY_INTENT);

            }
        });

        refUser.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                city.setText(user.getCity());
                description.setText(user.getDescription());
                email.setText(user.getEmail());
                if(!(user.getProfilePhotoUrl().isEmpty())){
                    httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(user.getProfilePhotoUrl());
                    Glide.with(UpdateDataUser.this)
                            .using(new FirebaseImageLoader())
                            .load(httpsReference)
                            .into(uploadPhoto);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("Error no hay datos en la base de datos! :P"+databaseError.toString());
            }
        });


        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(name.getText().toString());
                user.setCity(city.getText().toString().trim());
                user.setDescription(description.getText().toString().trim());
                if(!descargarFoto.toString().isEmpty()){
                    user.setProfilePhotoUrl(descargarFoto.toString());
                }
                refUser.child(mAuth.getCurrentUser().getUid()).setValue(user);
                startActivity(new Intent(UpdateDataUser.this, NavigationPanel.class));
                finish();

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALERY_INTENT && resultCode == RESULT_OK){
            myProgressDialog.setTitle("subiendo...");
            myProgressDialog.setMessage("Subiendo de perfil");
            myProgressDialog.setCancelable(false);
            myProgressDialog.show();

            Uri urlPhoto = data.getData();

            StorageReference filePath = myStorage.child("fotos/"+mAuth.getCurrentUser().getUid()+"/profile/"+urlPhoto.
                    getLastPathSegment()+"profile");

            filePath.putFile(urlPhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myProgressDialog.dismiss();

                    descargarFoto= taskSnapshot.getDownloadUrl();
                    Glide.with(UpdateDataUser.this).load(descargarFoto).fitCenter()
                            .centerCrop().into(uploadPhoto);

                    Toast.makeText(UpdateDataUser.this, "Se subio correctamente la foto", Toast.LENGTH_SHORT).show();
                    user.setProfilePhotoUrl(descargarFoto.toString());

                }
            });
        }
    }
}
