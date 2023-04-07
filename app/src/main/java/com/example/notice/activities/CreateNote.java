package com.example.notice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notice.R;
import com.example.notice.database.NoteDatabase;
import com.example.notice.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNote extends AppCompatActivity {

    EditText noteTitle, noteContent;
    ImageView backButton, doneButton;
    TextView timeAndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        initializeVariables();
        // back to previous page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // display today date and the current time
        timeAndDate.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }
    private void initializeVariables() {
        backButton = findViewById(R.id.back_button);
        doneButton = findViewById(R.id.done_button);
        noteTitle = findViewById(R.id.note_title);
        noteContent = findViewById(R.id.note_content);
        timeAndDate = findViewById(R.id.time_and_date);
    }

    private void saveNote(){
        if (noteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (noteTitle.getText().toString().trim().isEmpty() &&
                noteContent.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        final Note note = new Note();
        note.setTitle(noteTitle.getText().toString());
        note.setContent(noteContent.getText().toString());
        note.setDate(timeAndDate.getText().toString());
        // this class to save data because the Room database doesn't allow
        // to do this operations on the main thread
        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                NoteDatabase.getInstance(getApplicationContext()).dao().insert(note);
                return null;
            }
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new SaveNoteTask().execute();
    }
}