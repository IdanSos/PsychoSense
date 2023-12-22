package com.example.psychosense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DictionaryActivity extends AppCompatActivity {

    Button flashcardsBtn, listBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        replaceFragment(new DictionaryFlashcardsFragment());

        //fragments
        flashcardsBtn = findViewById(R.id.flashcardsBtn);
        listBtn = findViewById(R.id.listBtn);

        flashcardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {replaceFragment(new DictionaryFlashcardsFragment());
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {replaceFragment(new DictionaryListFragment());
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        // Set Dictionary selected
        bottomNavigationView.setSelectedItemId(R.id.dictionary);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.math) {
                    startActivity(new Intent(getApplicationContext(), MathActivity.class));
                    overridePendingTransition(0, 0);
                }

                else if (item.getItemId() == R.id.dictionary) {
                    return true;
                }

                else if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0, 0);
                }

                return false;
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}