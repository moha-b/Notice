package com.example.notice.adapter;

import com.example.notice.entities.Note;

public interface NoteListener {
    void onNoteClicked(Note note, int position);
}
