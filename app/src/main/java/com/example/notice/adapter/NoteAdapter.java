package com.example.notice.adapter;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notice.R;
import com.example.notice.entities.Note;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }
    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.note_layout, parent, false));
    }
    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitle, noteContent, timeAndDate;
        ConstraintLayout noteLayout;
        // initialize
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title_tv);
            noteContent = itemView.findViewById(R.id.note_content_tv);
            timeAndDate = itemView.findViewById(R.id.time_and_date_tv);
            noteLayout = itemView.findViewById(R.id.note_layout);
        }

        void setNote(Note note){
            noteTitle.setText(note.getTitle());
            if (note.getContent().trim().isEmpty()){
                noteContent.setVisibility(View.GONE);
            }else {
                noteContent.setText(note.getContent());
            }
            timeAndDate.setText(note.getDate());

            GradientDrawable drawable = (GradientDrawable) noteLayout.getBackground();
            if (note.getColor() != null && !note.getColor().isEmpty()){
                drawable.setColor(Color.parseColor(note.getColor()));
            }else {
                drawable.setColor(Color.parseColor("#333333"));
            }
        }

    }
}