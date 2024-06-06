package com.university.hafsa.view.Fragments.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.university.hafsa.view.activities.LoginActivity;
import com.university.hafsa.R;
import com.university.hafsa.view.Fragments.Mother;

public class Details extends Mother {

private TextView back;
    private TextView des;
    private int desTxt;
    public Details() {
        // Required empty public constructor
    }

    public static Details newInstance() {
        return new Details();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);


        des = view.findViewById(R.id.des);

        // Set up the click listener for the back button
        back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                replaceFragment(login);
            }
        });


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (desTxt != 0) {
            des.setText(desTxt);
        }
    }

    public void setDescriptionText(int text) {
        desTxt = text;
        if (des != null) {
            des.setText(text);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Show the toolbar when this fragment is attached
        LoginActivity activity = (LoginActivity) getActivity();
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setVisibility(View.VISIBLE);
        }

    }
}
