package com.example.psychosense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "Idan";

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    // Declaration of variables
    ImageButton submitBtn;
    Button loginBtn;
    EditText usernameET, emailET, passwordET;
    Intent intent;
    FirebaseDatabase mDatabase;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // connect the variables to the xml views and listen to them
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        usernameET = findViewById(R.id.usernameET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        //connect to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference().getDatabase();
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginBtn) {
            // move the user to login activity
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        //add comments later!!!!
        else if (v.getId() == R.id.submitBtn) {
            if (passwordET.getText() != null && emailET.getText() != null) {
                String email = (emailET.getText().toString()),
                        password = (passwordET.getText().toString()),
                        username = (usernameET.getText().toString());
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //creating a unique id
                            UID = user.getUid();
                            //creating
                            mDatabase.getReference().child("user").child(UID).setValue(username);

                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // if register fails, display a message to the user
                            Log.d(TAG, "onComplete: failed auth");
                            Toast.makeText(Register.this, "Authentication Faild", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(Register.this, "enter password or email", Toast.LENGTH_SHORT).show();
            }
        }
    }
}