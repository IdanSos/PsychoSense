package com.example.psychosense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button notificationBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notificationBtn = findViewById(R.id.notificationBtn);
        notificationBtn.setOnClickListener(this);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.math) {
                    startActivity(new Intent(getApplicationContext(), MathActivity.class));
                    overridePendingTransition(0, 0);
                }

                else if (item.getItemId() == R.id.dictionary) {
                    startActivity(new Intent(getApplicationContext(), DictionaryActivity.class));
                    overridePendingTransition(0, 0);
                }

                else if (item.getItemId() == R.id.home) {
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.notificationBtn) {
            // move the user to __ activity
            Intent intent = new Intent(this, CreateNotificationActivity.class);
            startActivity(intent);
        }
    }
}