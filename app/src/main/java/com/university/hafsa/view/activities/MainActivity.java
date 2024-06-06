package com.university.hafsa.view.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.university.hafsa.R;
import com.university.hafsa.controller.Controller;
import com.university.hafsa.controller.MySP;
import com.university.hafsa.model.dao.GeneralDao;
import com.university.hafsa.model.entity.Lesson;
import com.university.hafsa.view.Fragments.main.Main;

import java.util.List;

public class MainActivity extends ActivityMother {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ActionBarDrawerToggle toggle;
    Main main = new Main();
    private Controller controller;
    private MySP mySP;
    Toolbar toolbar;
    MenuItem notificationItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main, main);
        fragmentTransaction.commit();
        FirebaseApp.initializeApp(MainActivity.this);


        controller = new Controller(MainActivity.this);
        mySP = new MySP(MainActivity.this);


        View headerv = navigationView.getHeaderView(0);
        TextView userName = headerv.findViewById(R.id.textView);
        userName.setText("Hi"+ " " + mySP.getMyString("user"));




//@todo this is used to add lessons programmatically to the database

       controller = new Controller(this);

//        GeneralDao csharpDao = new GeneralDao("IT_Security");
//        Lesson csharp = new Lesson();
//        csharp.setId(0);
//       csharp.setLesson("Artificial intelligence (AI) makes it possible for machines to learn from experience, adjust to new inputs and perform human-like tasks");
//        csharpDao.createLesson(csharp);
//        csharp.setId(1);
//        csharp.setLesson("Artificial intelligence (AI) makes it possible for machines to learn from experience, adjust to new inputs and perform human-like tasks");
//        csharpDao.createLesson(csharp);
//        csharp.setId(2);
//        csharp.setLesson("Artificial intelligence (AI) makes it possible for machines to learn from experience, adjust to new inputs and perform human-like tasks");
//        csharpDao.createLesson(csharp);

        //msg = main.findViewById(R.id.msg);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id._deconect) {
                mySP.saveMyString("user","");
                logout();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (menuItem.getItemId() == R.id._shareApp) {
                sharTheApp();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (menuItem.getItemId() == R.id._exitApp) {
                exitTheApp(MainActivity.this, this);

                drawerLayout.closeDrawer(GravityCompat.START);
            }

            if (menuItem.getItemId() == R.id._Ai) {

                // Update UI with the new lessons
                controller.getAllLessons("Artificial_Intelligence").observe(MainActivity.this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (menuItem.getItemId() == R.id._SW) {
                // Update UI with the new lessons
                controller.getAllLessons("Scientific_Writing").observe(MainActivity.this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (menuItem.getItemId() == R.id._SSD) {
                controller.getAllLessons("Structured_Seeding_Data").observe(MainActivity.this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (menuItem.getItemId() == R.id._MA) {
                controller.getAllLessons("Mobile_Applications").observe(MainActivity.this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (menuItem.getItemId() == R.id._Startup) {
                controller.getAllLessons("Startup").observe(MainActivity.this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (menuItem.getItemId() == R.id._ITSecurity) {
                controller.getAllLessons("IT_Security").observe(MainActivity.this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }

            return false;
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle intent data if the activity is resumed from a notification click
        setIntent(intent);
        handleIntent(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification, menu);
        notificationItem = menu.findItem(R.id._notifications);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id._notifications) {
            controller.getAllLessons("announcement").observe(MainActivity.this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);

        }
        return true;
    }
    private void init() {
        drawerLayout = findViewById(R.id.drawer);
         toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navmenu);
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.setDrawerSlideAnimationEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }


    private void updateLessonsInMainFragment(List<String> lessons) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Main mainFragment = (Main) fragmentManager.findFragmentById(R.id.main);
        if (mainFragment != null) {
            mainFragment.updateLessons(lessons);
        }
    }


    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish MainActivity
    }


    private void handleIntent(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            for (String key : bundle.keySet()) {
                String value = bundle.getString(key);
                Log.d(TAG, "key: " + key + ", value: " + value);

                Lesson lesson= new Lesson();
                lesson.setId(Integer.parseInt(key));
                lesson.setLesson(value);
                GeneralDao generalDao = new GeneralDao("announcement");
                generalDao.createLesson(lesson);

            }
            // Call the method after extracting the bundle data
            controller.getAllLessons("announcement").observe(this.main.getViewLifecycleOwner(), this::updateLessonsInMainFragment);
        }
    }
}
