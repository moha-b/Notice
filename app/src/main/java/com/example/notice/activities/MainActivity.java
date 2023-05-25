package com.example.notice.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notice.R;
import com.example.notice.adapter.NoteAdapter;
import com.example.notice.adapter.NoteListener;
import com.example.notice.database.NoteDatabase;
import com.example.notice.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteListener {

    private static final String TAG = "MainActivity";
    // Declare an ActivityResultLauncher object
    private ActivityResultLauncher<Intent> launcher;
    MediaPlayer player;
    ImageView addNote;
    EditText search;
    RecyclerView notesRecyclerView;
    List<Note> noteList;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initial
        addNote = findViewById(R.id.add_note);
        search = findViewById(R.id.search);
        // TODO 1: initialize the RecyclerView
        notesRecyclerView = findViewById(R.id.recycler_view);
        // TODO 2: give the RecyclerView a shape
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        // TODO 3: initialize the list for adapter
        noteList = new ArrayList<>();
        // TODO 4: initialize the Adapter
        adapter = new NoteAdapter(noteList, this);
        // TODO 5: a sign Adapter for the recycler view
        notesRecyclerView.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the list is empty of not
                if (noteList.size() != 0) {
                    adapter.search(editable.toString());
                }
            }
        });
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(new Intent(getApplicationContext(), CreateNote.class));
            }
        });
        // initialize recycler view with the required properties
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

    private void getNotes() {
        // Get all notes saved in database
        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {
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
                notesRecyclerView.smoothScrollToPosition(0);
            }
        }
        new GetNotesTask().execute();
    }

    public void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.mii_theme);
        }

        player.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    public void stop() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        Intent intent = new Intent(getApplicationContext(), CreateNote.class);
        intent.putExtra("isView", true);
        intent.putExtra("note", note);
        launcher.launch(intent);
    }
}