package com.example.deliverynestpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class PartnerDashboard extends AppCompatActivity {

String username;
    SessionManager sessionManager;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_dashboard);
        sessionManager = new SessionManager(this);
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        username = usersDetails.get(SessionManager.KEY_USERNAME);

        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setBackground(null);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId())
                {
                    case R.id.bottom_nav_dashboard:
                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragment, "");
                        fragmentTransaction.commit();
                        return true;
                    case R.id.bottom_nav_today_delivery:
                        TodayDelivery fragment1 = new TodayDelivery();
                        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.content, fragment1);
                        fragmentTransaction1.commit();
                        return true;
                    case R.id.bottom_nav_history:
                        PartnerHistory fragment2 = new PartnerHistory();
                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.content, fragment2, "");
                        fragmentTransaction2.commit();
                        return true;
                    case R.id.bottom_nav_update_status:
                        UpdateStatus fragment3 = new UpdateStatus();
                        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.content, fragment3, "");
                        fragmentTransaction3.commit();
                        return true;
                }
                return false;
            }
        });
    }
}