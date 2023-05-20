package com.example.notice.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notice.R;
import com.example.notice.database.NoteDatabase;
import com.example.notice.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNote extends AppCompatActivity {

    static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    ActivityResultLauncher<Intent> galleryLauncher;
    EditText noteTitle, noteContent;
    ImageView backButton, doneButton;
    RoundedImageView uploadedImage;
    TextView timeAndDate;
    String color ;
    String imagePath;

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
        initializeBottomSheet();
        // Define a new ActivityResultLauncher to handle the gallery image selection
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Get the selected image URI from the intent
                        Uri imageUri = result.getData().getData();
                        try {
                            // Load the image from URI as a Bitmap
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            uploadedImage.setImageBitmap(bitmap);
                            imagePath = getImagePathFromUri(imageUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("CreateNote.java :: galleryLauncher at line 79");
                        }
                    }
                });
    }
    private void initializeVariables() {
        color = "";
        imagePath ="";
        backButton = findViewById(R.id.back_button);
        doneButton = findViewById(R.id.done_button);
        noteTitle = findViewById(R.id.note_title);
        noteContent = findViewById(R.id.note_content);
        timeAndDate = findViewById(R.id.time_and_date);
        uploadedImage = findViewById(R.id.image_view);
    }

    private void saveNote(){
        // TODO 1: check if the note title not empty
        // TODO 2: save the user inputs in the database
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
        note.setColor(color);
        note.setImagePath(imagePath);
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
    private void initializeBottomSheet(){
        final LinearLayout bottomSheet = findViewById(R.id.bottom_sheet_layout);
        View noteColor = bottomSheet.findViewById(R.id.note_color);
        BottomSheetBehavior<LinearLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        GradientDrawable drawable = (GradientDrawable) noteColor.getBackground();
        final ImageView imageColor1 = bottomSheet.findViewById(R.id.im_color_1);
        final ImageView imageColor2 = bottomSheet.findViewById(R.id.im_color_2);
        final ImageView imageColor3 = bottomSheet.findViewById(R.id.im_color_3);
        final ImageView imageColor4 = bottomSheet.findViewById(R.id.im_color_4);
        final ImageView imageColor5 = bottomSheet.findViewById(R.id.im_color_5);
        color = "#333333";
        drawable.setColor(Color.parseColor(color));
        imageColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#333333";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#FDBE3B";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#FF4842";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#3A52FC";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#673AB7";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);

                drawable.setColor(Color.parseColor(color));
            }
        });

        bottomSheet.findViewById(R.id.upload_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromGallery();
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
    private void selectImageFromGallery() {
        // Check if the app has permission to read external storage
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted, request it
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_STORAGE_PERMISSION
            );
        } else {
            // If permission is granted, use the galleryLauncher to start the image picking intent
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission is granted, use the galleryLauncher to start the image picking intent
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            } else {
                // If permission is denied, show a message or take appropriate action
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getImagePathFromUri(Uri uri) {
        String filePath;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            filePath = uri.getPath();
        }else{
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow("_data");
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

}