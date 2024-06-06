package com.university.hafsa.view.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.university.hafsa.R;
import com.university.hafsa.view.Fragments.login.Details;
import com.university.hafsa.view.Fragments.login.Login;

public class LoginActivity extends ActivityMother {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Login login = new Login();
    Details details;
//@todo go to : Firebase In-App Messaging API url and activathe the api in the google cloud console
    //@todo https://console.cloud.google.com/apis/api/firebaseinappmessaging.googleapis.com/metrics?project=hafsa-b3030
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_login, login);
        fragmentTransaction.commit();
        details = new Details();

        // Use a FragmentLifecycleCallback to ensure the fragment is attached
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                if (f instanceof Details) {
                    //((Presentation) f).setDescriptionText("New Description Text");
                    navigationView.setNavigationItemSelectedListener(menuItem -> {
                        if (menuItem.getItemId() == R.id.LMD1) {
                            // Handle navigation view item clicks here
                            Bundle bundle = new Bundle();
                            if (bundle.containsKey("math")) {
                                ((Details) f).setDescriptionText(R.string.lmd1);
                            }

                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        if (menuItem.getItemId() == R.id.LMD2) {
                            ((Details) f).setDescriptionText(R.string.lmd2);
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        if (menuItem.getItemId() == R.id.LMD3) {
                            ((Details) f).setDescriptionText(R.string.lmd3);
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        //@todo complete the other
                        return false;
                    });
                }
            }
        }, false);


    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navmenu);
        navigationView.setItemIconTintList(null);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.setDrawerSlideAnimationEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }




}
