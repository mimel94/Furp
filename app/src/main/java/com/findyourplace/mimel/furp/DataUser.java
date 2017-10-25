package com.findyourplace.mimel.furp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.findyourplace.mimel.furp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mimel on 12/09/17.
 */

public class DataUser extends AppCompatActivity {
    Button EditData;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference refUser;
    TextView name;
    TextView city;
    TextView description;
    TextView email;
    User user;

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

    public void onCreate(Bundle saveInstanceBundle){
        super.onCreate(saveInstanceBundle);
        setContentView(R.layout.user_data);
        mAuth = FirebaseAuth.getInstance();
        isLoged();
        user = new User();
        name = (TextView)findViewById(R.id.txtName);
        city = (TextView)findViewById(R.id.txtCity);
        description = (TextView)findViewById(R.id.txtDescription);
        email = (TextView)findViewById(R.id.txtEmail);
        database = FirebaseDatabase.getInstance();
        refUser = database.getReference("user");

        refUser.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                if(user.getName().isEmpty()){
                    name.setText("Complete su nombre");
                }else {
                    name.setText(user.getName());
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
                email.setText(user.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("Error no hay datos en la base de datos! :P"+databaseError.toString());
            }
        });
        mAuth.getCurrentUser();


    }
    public void isLoged(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("", "Actualmente esta logeado:" + user.getUid());
                    //welcomeUser();
                } else {
                    // User is signed out
                    Log.d("", "No hay usuario logeado");
                }
                // ...
            }
        };
    }
}
