package com.example.psychosense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Login extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "Idan";
    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    // Declaration of variables
    ImageButton submitBtn;
        Button registerBtn;
        EditText emailET, passwordET;
        Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connect the variables to the xml views and listen to them
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        //connect to firebase
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerBtn) {
            // move the user to register activity
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }

        //add comments later!!!
        else if (v.getId() == R.id.submitBtn) {
            if (passwordET.getText() != null && emailET.getText() != null) {
                String email = (emailET.getText().toString()),
                        password = (passwordET.getText().toString());
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(Login.this, "enter password or email", Toast.LENGTH_SHORT).show();
            }
        }
    }
}