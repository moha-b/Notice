package com.example.notice.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.notice.R;
import com.example.notice.adapter.NoteAdapter;
import com.example.notice.database.NoteDatabase;
import com.example.notice.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // Declare an ActivityResultLauncher object
    private ActivityResultLauncher<Intent> launcher;
    ImageView addNote;
    RecyclerView notesRecyclerView;
    List<Note> noteList;
    NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNote = findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the activity using the launcher
                launcher.launch(new Intent(getApplicationContext(), CreateNote.class));
            }
        });
        // initialize recycler view with the required properties
        notesRecyclerView = findViewById(R.id.recycler_view);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        noteList = new ArrayList<>();
        adapter = new NoteAdapter(noteList);
        notesRecyclerView.setAdapter(adapter);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Handle the result in the callback function
                    if (result.getResultCode() == RESULT_OK) {
                        // Result is OK, handle success
                        getNotes();
                    } else {
                        // Result is not OK, handle failure or cancellation
                        Log.e(TAG, "Error: " + result.getResultCode());
                    }
                });
        // Call getNotes method to retrieve and display notes from the database
        getNotes();
    }

    private void getNotes(){
        // Get all notes saved in database
        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>>{
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getInstance(getApplicationContext())
                        .dao().getAllNotes();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                noteList.clear(); // Clear noteList before adding retrieved notes
                noteList.addAll(notes);
                adapter.notifyDataSetChanged();
                notesRecyclerView.smoothScrollToPosition(0);
                //notesRecyclerView.smoothScrollToPosition(0);
            }
        }
        new GetNotesTask().execute();
    }
}