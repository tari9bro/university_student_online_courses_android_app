package com.university.hafsa.view.Fragments.login;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.university.hafsa.controller.MySP;
import com.university.hafsa.view.activities.LoginActivity;
import com.university.hafsa.view.activities.MainActivity;
import com.university.hafsa.R;
import com.university.hafsa.controller.Controller;
import com.university.hafsa.view.Fragments.Mother;

import java.util.concurrent.atomic.AtomicReference;

public class Login extends Mother {


    private ImageView logoImageView;
    private TextView presentation_id,dontHaveAccountTextView;
    private EditText nameEditText, passwordEditText;
    private MaterialButton loginButton;
   private Controller controller;
   private MySP mySP;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homeview = inflater.inflate(R.layout.login, container, false);


        logoImageView = homeview.findViewById(R.id.logoImageView);
        presentation_id = homeview.findViewById(R.id.preId);
        dontHaveAccountTextView = homeview.findViewById(R.id.dontHaveAccountTextView);
        nameEditText = homeview.findViewById(R.id.nameEditText);
        passwordEditText = homeview.findViewById(R.id.passwordEditText);
        loginButton = homeview.findViewById(R.id.loginButton);

        controller = new Controller(requireActivity());
        mySP = new MySP(requireActivity());
        // Check if user is already logged in
        if (!mySP.getMyString("user").isEmpty()) {
            redirectToMainActivity();
        }else{
            requestNotificationPermission();
        }

        dontHaveAccountTextView.setOnClickListener(v -> {
            if (loginButton.getText().toString().equals("Login")) {
            loginButton.setText("Register");
            dontHaveAccountTextView.setText("Already have an account? Login");}
            else {
                loginButton.setText("Login");
                dontHaveAccountTextView.setText("Don't have an account? Register");
            }
        });
        loginButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please enter both name and password", Toast.LENGTH_SHORT).show();
            } else {
                if (loginButton.getText().toString().equals("Login")) {
                    // Login
                    // Login
                    controller.loginUser(name, password, new Controller.AuthStatus() {
                        @Override
                        public void AuthResult(boolean isAuthenticated) {
                            if (isAuthenticated) {
                                redirectToMainActivity();
                            }else {
                                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                   }
                else {
                    controller.registerUser(name, password);
                }
            }
        });
        Details details = new Details();
        logoImageView.setOnClickListener(v -> {

            replaceFragment(details);
        });
        presentation_id.setOnClickListener(v -> {
            replaceFragment(details);
        });
        return homeview;
    }



    private void redirectToMainActivity() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Hide the toolbar when this fragment is attached
        LoginActivity activity = (LoginActivity) context;
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);
        }
    }

    private void requestNotificationPermission() {
        // Check if the app is running on Android 13 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if the POST_NOTIFICATIONS permission is granted
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Show dialog to request permission
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Notification Permission")
                        .setMessage("This app requires permission to send notifications. Please grant the permission to stay updated.")
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Request the POST_NOTIFICATIONS permission
                                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
                            }
                        })
                        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle the case where the user denies the permission
                                Log.w(TAG, "Notification permission not granted");
                            }
                        })
                        .show();
            } else {
                Log.i(TAG, "Notification permission already granted");
            }
        }
    }
}
