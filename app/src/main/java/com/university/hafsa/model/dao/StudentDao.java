package com.university.hafsa.model.dao;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.university.hafsa.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private DatabaseReference databaseReference;
    private int idCounter = 0; // To keep track of the next available ID

    public StudentDao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("students");
        initializeIdCounter();
    }

    // Initialize the ID counter based on existing data
    private void initializeIdCounter() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Student student = dataSnapshot.getValue(Student.class);
                    if (student != null && student.getId() > idCounter) {
                        idCounter = student.getId();
                    }
                }
                idCounter++; // Ensure the next ID is unique
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    public void addStudent(Student student) {
        student.setId(idCounter++);
        databaseReference.child(String.valueOf(student.getId())).setValue(student);
    }

    public void getStudent(int id, final DataStatus dataStatus) {
        databaseReference.child(String.valueOf(id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                dataStatus.DataIsLoaded(student);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataStatus.DataIsCancelled(databaseError);
            }
        });
    }

    public void getAllStudents(final DataStatus dataStatus) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Student> students = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    students.add(student);
                }
                dataStatus.DataIsLoaded(students);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataStatus.DataIsCancelled(databaseError);
            }
        });
    }

    public void updateStudent(Student student) {
        databaseReference.child(String.valueOf(student.getId())).setValue(student);
    }

    public void deleteStudent(int id) {
        databaseReference.child(String.valueOf(id)).removeValue();
    }

    public void verifyUser(String name, String password, final AuthStatus authStatus) {
        databaseReference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isVerified = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student student = snapshot.getValue(Student.class);
                    if (student != null && student.getPassword().equals(password)) {
                        isVerified = true;
                        break;
                    }
                }
                authStatus.AuthResult(isVerified);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                authStatus.AuthCancelled(databaseError);
            }
        });
    }

    public interface DataStatus {
        void DataIsLoaded(Object data);

        void DataIsCancelled(DatabaseError error);
    }

    public interface AuthStatus {
        void AuthResult(boolean isAuthenticated);

        void AuthCancelled(DatabaseError error);
    }
}
