package com.findyourplace.mimel.furp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.findyourplace.mimel.furp.models.User;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NavigationPanel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference refUser;
    TextView city;
    TextView description;
    TextView email;
    User user;
    TextView salute;
    ImageView profilePhoto;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_panel);
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        profilePhoto = (ImageView)findViewById(R.id.img_profile_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        salute = (TextView)findViewById(R.id.txt_welcome);
        city = (TextView)findViewById(R.id.txtCity);
        description = (TextView)findViewById(R.id.txtDescription);
        email = (TextView)findViewById(R.id.txtEmail);
        database = FirebaseDatabase.getInstance();
        refUser = database.getReference("user");
        refUser.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(!user.getName().isEmpty()){
                    salute.setText("Hola "+user.getName());
                }else{
                    salute.setText("Hola, por favor actualize su nombre");
                }

                if(user.getCity().isEmpty()){
                    city.setText("Complete su ciudad");
                }else {
                    city.setText(user.getCity());
                }
                if(user.getDescription().isEmpty()){
                    description.setText("Complete su descripcion");
                }else {
                    description.setText(user.getDescription());
                }
                if(!user.getProfilePhotoUrl().isEmpty()){
                    StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(user.getProfilePhotoUrl());
                    Glide.with(NavigationPanel.this)
                            .using(new FirebaseImageLoader())
                            .load(httpsReference)
                            .into(profilePhoto);
                }
                email.setText(user.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("Error no hay datos en la base de datos! :P"+databaseError.toString());
            }
        });

        mAuth.getCurrentUser();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit_data) {
            startActivity(new Intent(this, UpdateDataUser.class));
        } else if (id == R.id.publish_site) {
            startActivity(new Intent(this, PublishSite.class));

        } else if (id == R.id.my_sites) {
            startActivity(new Intent(this, ViewPublishSites.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
