package com.university.hafsa.view.Fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.university.hafsa.R;
import com.university.hafsa.controller.Controller;
import com.university.hafsa.controller.LessonAdapter;
import com.university.hafsa.controller.MySP;
import com.university.hafsa.view.Fragments.Mother;

import java.util.ArrayList;
import java.util.List;


public class Main extends Mother {
    private RecyclerView recyclerView;
    private LessonAdapter lessonAdapter;
    private Controller controller;
    private MySP mySP;

    public Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainFragment = inflater.inflate(R.layout.main, container, false);
        mySP = new MySP(requireActivity());
      //  msg = mainFragment.findViewById(R.id.msg);


       // nameTextView.setText(mySP.getMyString("user"));
        recyclerView = mainFragment.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        lessonAdapter = new LessonAdapter(new ArrayList<>());
        recyclerView.setAdapter(lessonAdapter);
        return mainFragment;
    }
    public void updateLessons(List<String> lessonList) {
        if (lessonAdapter != null) {
            lessonAdapter.updateLessonList(lessonList);
        }

}
}
