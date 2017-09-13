package com.findyourplace.mimel.furp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.findyourplace.mimel.furp.models.FirebaseReferences;
import com.findyourplace.mimel.furp.models.Site;
import com.findyourplace.mimel.furp.objects.Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPublishSites extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Site> sites;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference refSite;
    Adapter adapter;
    Button backMenu;

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
        setContentView(R.layout.activity_publish_sites);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        sites = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        isLoged();
        database = FirebaseDatabase.getInstance();
        adapter = new Adapter(sites);
        recyclerView.setAdapter(adapter);
        refSite = database.getReference(FirebaseReferences.SITE_REFERENCE);
        Log.d("", "DATA!!!:" + refSite);
        refSite.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                sites.removeAll(sites);
                for (DataSnapshot snapshot:
                     dataSnapshot.getChildren()) {
                    Site site = snapshot.getValue(Site.class);
                    Log.d("", "DATA!!!:" + snapshot.getValue());
                    sites.add(site);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        backMenu = (Button)findViewById(R.id.btnBack);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPublishSites.this, WelcomeUser.class);
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
