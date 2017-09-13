package com.findyourplace.mimel.furp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.findyourplace.mimel.furp.models.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mimel on 5/09/17.
 */

public class WelcomeUser extends AppCompatActivity {
    Button editAndView;
    Button publishSite;
    Button viewSites;

    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference refUser;

    Button closeSesion;
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.user_welcome);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refUser = database.getReference(FirebaseReferences.USER_REFERENCE);

        editAndView = (Button)findViewById(R.id.btn_data_user);
        editAndView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeUser.this, DataUser.class);
                startActivity(i);
            }
        });
        publishSite = (Button)findViewById(R.id.btnPublish);
        publishSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeUser.this, PublishSite.class);
                startActivity(i);
            }
        });
        viewSites = (Button)findViewById(R.id.btnViewSites);
        viewSites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeUser.this, ViewPublishSites.class);
                startActivity(i);
            }
        });
        closeSesion = (Button)findViewById(R.id.btnCloseSession);
        closeSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "Actualmente esta logeado:" + mAuth.getCurrentUser().toString());
                mAuth.signOut();
                Intent i = new Intent(WelcomeUser.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

}
