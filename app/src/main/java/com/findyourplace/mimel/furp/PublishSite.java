package com.findyourplace.mimel.furp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.findyourplace.mimel.furp.models.FirebaseReferences;
import com.findyourplace.mimel.furp.models.Site;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublishSite extends AppCompatActivity {
    Button create;
    EditText name;
    DatabaseReference refSite;
    EditText location;
    EditText city;
    EditText description;
    EditText type;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
        isLoged();
        create = (Button)findViewById(R.id.btnCreate);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText)findViewById(R.id.txtName);
        location = (EditText)findViewById(R.id.txtLocation);
        city = (EditText)findViewById(R.id.txtCity);
        description = (EditText)findViewById(R.id.txtDescription);
        type = (EditText)findViewById(R.id.txtType);
        database = FirebaseDatabase.getInstance();
        refSite = database.getReference(FirebaseReferences.SITE_REFERENCE);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Site place =  new Site(name.getText().toString(), location.getText().toString(),
                        city.getText().toString(), description.getText().toString(),
                        type.getText().toString());
                refSite.child(mAuth.getCurrentUser().getUid()).setValue(place);
                Intent i = new Intent(PublishSite.this, WelcomeUser.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void isLoged(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("", "Actualmente esta logeado:" + user.getUid());
                    //WelcomeUser();
                } else {
                    // User is signed out
                    Log.d("", "No hay usuario logeado");
                }
                // ...
            }
        };
    }
}
