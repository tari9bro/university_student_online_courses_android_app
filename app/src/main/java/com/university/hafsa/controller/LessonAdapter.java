package com.university.hafsa.controller;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.university.hafsa.R;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<String> lessonList;

    public LessonAdapter(List<String> lessonList) {
        this.lessonList = lessonList;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        String lesson = lessonList.get(position);
        holder.lessonTextView.setText(lesson);
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public void updateLessonList(List<String> newLessonList) {
        lessonList = newLessonList;
        notifyDataSetChanged();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTextView;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonTextView = itemView.findViewById(R.id.lessonTextView);
        }
    }
}
