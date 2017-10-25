package com.findyourplace.mimel.furp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findyourplace.mimel.furp.models.FirebaseReferences;
import com.findyourplace.mimel.furp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mimel on 5/09/17.
 */

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText email;
    EditText password;
    Button btnRegister;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(FirebaseReferences.USER_REFERENCE);

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();


       /* btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText)findViewById(R.id.txtEmail);
                password = (EditText)findViewById(R.id.txtPassword);

                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterUser.this,"Usuario creado correctamente: "+mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(RegisterUser.this,"Algo paso, usuario ya registrado o datos mal ingresados: ", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });*/

    }
    public void createUser(View view){
        email = (EditText)findViewById(R.id.txtEmail);
        password = (EditText)findViewById(R.id.txtPassword);

        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterUser.this,"Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                            User user = new User("","","",mAuth.getCurrentUser().getEmail().toString(),"");
                            myRef.child(mAuth.getCurrentUser().getUid()).setValue(user);

                            finish();

                        }else{
                            Toast.makeText(RegisterUser.this,"Algo paso, usuario ya registrado o datos mal ingresados: ", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
