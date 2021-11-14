package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.InternalTokenProvider;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment1()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            if(item.getItemId()==R.id.profile_bottom_menu_item){
                selectedFragment = new Fragment1();
            }
            if(item.getItemId()==R.id.ask_bottom_menu_item){
                selectedFragment = new Fragment2();
            }
            if(item.getItemId()==R.id.request_bottom_menu_item){
                selectedFragment = new Fragment3();
            }
            if(item.getItemId()==R.id.home_bottom_menu_item){
                selectedFragment = new Fragment4();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selectedFragment).commit();

            return true;
        }
    };




/**
    public void logout(View view){

        mAuth.signOut();

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
**/

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

}