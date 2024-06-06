package com.university.hafsa.controller;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.university.hafsa.model.dao.GeneralDao;
import com.university.hafsa.model.dao.StudentDao;
import com.university.hafsa.model.entity.Lesson;
import com.university.hafsa.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final StudentDao studentDao = new StudentDao();

    private final MySP mySP;

   private final Activity activity;

    public Controller(Activity activity) {
        this.activity = activity;
        mySP = new MySP(activity);
    }

    public void registerUser(String name, String password) {
        Student student = new Student();
        student.setName(name);
        student.setPassword(password);
        studentDao.addStudent(student);
    }

    public void loginUser(final String name, final String password, final AuthStatus callback) {
        studentDao.verifyUser(name, password, new StudentDao.AuthStatus() {
            @Override
            public void AuthResult(boolean isAuthenticated) {
                if (isAuthenticated) {
                    mySP.saveMyString("user", name);
                    callback.AuthResult(true);
                } else {
                    callback.AuthResult(false);
                    Toast.makeText(activity.getApplicationContext(), "Invalid name or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void AuthCancelled(DatabaseError error) {
                Toast.makeText(activity.getApplicationContext(), "Authentication failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface AuthStatus {
        void AuthResult(boolean isAuthenticated);
    }




    public LiveData<List<String>> getAllLessons(String lessonType) {
        MutableLiveData<List<String>> lessonsLiveData = new MutableLiveData<>();
        GeneralDao generalDao = new GeneralDao(lessonType);

        generalDao.getLessons(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> lessons = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Lesson lesson = snapshot.getValue(Lesson.class);
                    if (lesson != null) {
                        lessons.add(lesson.getLesson());
                    }
                }
                lessonsLiveData.setValue(lessons);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        return lessonsLiveData;
    }

}
