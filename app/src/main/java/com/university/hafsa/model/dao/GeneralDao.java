package com.university.hafsa.model.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.university.hafsa.model.entity.Lesson;

public class GeneralDao {
    private DatabaseReference databaseReference;

    public GeneralDao(String lessonType) {
        databaseReference = FirebaseDatabase.getInstance().getReference(lessonType + "_lessons");
    }

    public void createLesson(Lesson lesson) {
        databaseReference.child(String.valueOf(lesson.getId())).setValue(lesson);
    }

    public void getLessons(ValueEventListener valueEventListener) {
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }
}
