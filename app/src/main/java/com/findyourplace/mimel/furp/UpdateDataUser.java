package com.findyourplace.mimel.furp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateDataUser extends AppCompatActivity {
    Button updateData;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference refUser;
    EditText name;
    EditText city;
    EditText description;
    EditText email;
    User user;

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
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        name = (EditText) findViewById(R.id.txtName);
        city = (EditText) findViewById(R.id.txtCity);
        description = (EditText) findViewById(R.id.txtDescription);
        email = (EditText) findViewById(R.id.txtEmail);
        database = FirebaseDatabase.getInstance();
        refUser = database.getReference("user");

        refUser.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                city.setText(user.getCity());
                description.setText(user.getDescription());
                email.setText(user.getEmail());
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
                refUser.child(mAuth.getCurrentUser().getUid()).setValue(user);
                startActivity(new Intent(UpdateDataUser.this, NavigationPanel.class));
                finish();

            }
        });

    }

}
