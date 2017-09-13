package com.findyourplace.mimel.furp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mimel on 5/09/17.
 */

public class WelcomeUser extends AppCompatActivity {
    Button editAndView;
    private FirebaseAuth mAuth;
    Button closeSesion;
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.user_welcome);
        editAndView = (Button)findViewById(R.id.btn_data_user);
        editAndView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeUser.this, DataUser.class);
                startActivity(i);
            }
        });
        closeSesion = (Button)findViewById(R.id.btnCloseSession);
        closeSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "Actualmente esta logeado:" + mAuth.getCurrentUser().toString());
            }
        });

    }

}
