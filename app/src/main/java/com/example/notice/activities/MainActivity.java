package com.example.notice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.notice.R;
import com.example.notice.database.NoteDatabase;
import com.example.notice.entities.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;

    ImageView addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNote = findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreateNote.class));
            }
        });
    }

    private void getNotes(){

        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>>{
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getInstance(getApplicationContext())
                        .dao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
            }
        }
        new GetNotesTask().execute();
    }
}