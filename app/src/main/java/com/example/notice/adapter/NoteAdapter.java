package com.example.notice.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notice.R;
import com.example.notice.entities.Note;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes; // List to hold the notes
    private List<Note> source; // Source list to be used for searching
    private NoteListener noteListener; // Listener interface for note click events
    private Timer timer; // Timer for delayed search

    public NoteAdapter(List<Note> notes, NoteListener noteListener) {
        this.notes = notes;
        this.source = notes;
        this.noteListener = noteListener;
    }

    // onCreateViewHolder is responsible for creating the ViewHolder
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the note_layout.xml file to create the View for the ViewHolder
        return new NoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false));
    }

    // onBindViewHolder is responsible for binding the data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setNote(notes.get(position)); // Set the note data for the ViewHolder
        // Set the click listener for the note layout
        holder.noteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Invoke the onNoteClicked method of the NoteListener interface
                // and pass the clicked note and its position
                noteListener.onNoteClicked(notes.get(position), position);
            }
        });
    }

    // getItemCount returns the total number of items in the adapter
    @Override
    public int getItemCount() {
        return notes.size();
    }

    // getItemViewType returns the view type of the item at the given position
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // NoteViewHolder is the ViewHolder class for the notes
    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteContent, timeAndDate;
        ConstraintLayout noteLayout;

        // Constructor to initialize the ViewHolder
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title_tv);
            noteContent = itemView.findViewById(R.id.note_content_tv);
            timeAndDate = itemView.findViewById(R.id.time_and_date_tv);
            noteLayout = itemView.findViewById(R.id.note_layout);
        }

        // setNote method is used to set the data for the ViewHolder
        void setNote(Note note) {
            noteTitle.setText(note.getTitle()); // Set the note title
            if (note.getContent().trim().isEmpty()) {
                noteContent.setVisibility(View.GONE); // Hide the note content TextView if it's empty
            } else {
                noteContent.setText(note.getContent()); // Set the note content
            }
            timeAndDate.setText(note.getDate()); // Set the note date and time

            GradientDrawable drawable = (GradientDrawable) noteLayout.getBackground();
            // Set the note layout background color
            if (note.getColor() != null && !note.getColor().isEmpty()) {
                drawable.setColor(Color.parseColor(note.getColor()));
            } else {
                drawable.setColor(Color.parseColor("#333333"));
            }
        }
    }

    // search method is used to filter the notes based on the search text
    public void search(String text) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (text.trim().isEmpty()) {
                    notes = source; // Reset the notes list to the source list if the search text is empty
                } else {
                    ArrayList<Note> list = new ArrayList<>();
                    // Iterate through the source list and add the matching notes to the new list
                    for (Note item : source) {
                        if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                            list.add(item);
                        }
                    }
                    notes = list; // Update the notes list with the filtered list
                }
                // Update the RecyclerView on the main thread
                new Handler(Looper.getMainLooper()) {
                }.post(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        notifyDataSetChanged(); // Notify the adapter that the data set has changed
                    }
                });
            }
        }, 300); // Delay the search execution by 300 milliseconds
    }

}