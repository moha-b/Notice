# Notice
A simple note-taking app using Java and Room Database is a practical and efficient solution for organizing your thoughts and ideas. With a clean and intuitive user interface, users can easily create, edit, and delete notes, as well as search for them using keywords or tags.

Using Room Database, the app provides a scalable data storage solution that can handle large amounts of data with ease, ensuring that users can store an unlimited number of notes without worrying about performance or storage limitations. Additionally, the app can be customized with a variety of themes and color schemes to suit individual preferences.

Overall, a simple note-taking app using Java and Room Database is an ideal tool for anyone who wants to stay organized and productive, whether you're a student, professional, or anyone in between.

## Ui Section
First of all we use the `constraint layout` alot
<img align="right" src="https://github.com/moha-b/Notice/assets/73842931/cf247085-c406-4c27-bcd5-9c4a279f2d40"/>
it makes any view with in it have a constrains. as u can see these blue circles represent the constraints, and the blue line here we tell the view that your start from the start of the screen, and the other line from the right side tells the view that you end at the end of the screen.

this is a little explanation about the Constraint layout form more watch this 
- [tutorial from 2:30](https://www.youtube.com/watch?v=VsgXFdynDuQ) (English)
- [tutorial](https://www.youtube.com/watch?v=9We9t_z4MoA) (Arabic)

we have 2 main screens and the note layout and the bottom sheet layout so in totall wwe have 4 designs

### Activity Main
```
Path : app/src/main/res/layout/activity_main.xml
```
![1and2-preview](https://github.com/moha-b/Notice/assets/73842931/b7705d14-dd86-4510-a364-284beca8f4f1)

in this image the upper part of our `activity_main.xml` we have two views here 1 & 2

1 - TextView : regular `TextView` with text containing our app name and here is the code
```xml
   <TextView
        android:id="@+id/title_tv"
        android:layout_width="wrap_content" // wrap_content : means the view will take the space he needs
        android:layout_height="wrap_content"
        android:layout_marginStart="@dimen/_16sdp" // margin : means the view will take space from a specific side start means left
        android:layout_marginTop="@dimen/_16sdp"
        android:text="@string/notice"  // this is the app name
        android:textColor="@color/white"
        android:textSize="@dimen/_20sdp"
        app:layout_constraintStart_toStartOf="parent" // this line is the constrint line 
        app:layout_constraintTop_toTopOf="parent"/>
```

2 - LinearLayout : it will rearrange the views within based on the given orientation (vertically or horizontally)
```xml
<!-- Search Bar -->
    <LinearLayout
        android:id="@+id/search_bar"
        android:layout_width="match_parent" // match_parent: tell the view to take the whole width or height for the screen
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="@dimen/_16sdp"
        android:layout_marginTop="@dimen/_16sdp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/title_tv"
        android:orientation="horizontal"        // here we tell the LinearLayout it will arrange the views horizontally
        android:background="@drawable/background_search" // here we give the LinearLayout a background how? 
        android:paddingHorizontal="@dimen/_10sdp"
        android:gravity="center" // to arrange the views in the center
        >
          // here is the views inside the LinearLayout
        <ImageView
            android:layout_width="@dimen/_20sdp"
            android:layout_height="@dimen/_20sdp"
            app:tint="@color/search_icon" // give it a color
            android:src="@drawable/ic_search"
            android:layout_marginEnd="@dimen/_8sdp"
            android:contentDescription="@string/search_icon"/> // contentDescription : for descrip the icon (no need for it)

        <EditText
            android:id="@+id/search"
            android:layout_width="match_parent"
            android:layout_height="@dimen/_40sdp"
            android:background="@null" // remove the background
            android:imeOptions="actionDone" // no need for it 
            android:inputType="text"  // specify the input type 
            android:hint="@string/looking_for"
            android:textColorHint="@color/hint"
            android:textColor="@color/white"
            android:textSize="@dimen/_13sdp"
            android:autofillHints="no" />
    </LinearLayout>
```
### How we can do this ?
```xml
   android:background="@drawable/background_search" // here we give the LinearLayout a background how?
```
### [Click Here](https://www.youtube.com/watch?v=MeCjfgR86MU)

---

![3and4-preview](https://github.com/moha-b/Notice/assets/73842931/d87170bb-dfb5-4c1a-814d-c58779a70f6e)

3 - RecyclerView : list that represent our data why we use it ? we want to show a lot of items, and the number of them is dynamic
```xml
 <!-- Recycler View -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="0dp"     // that's means take all the height available
        android:layout_marginHorizontal="@dimen/_16sdp"
        android:clipToPadding="false"
        android:paddingHorizontal="0dp"
        android:paddingVertical="@dimen/_12sdp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/search_bar"
        tools:layout_editor_absoluteX="3dp" />    // no need for it
```

4 - ImageView : regular `ImageView` with circle background and have a `plus` icon inside
```xml
  <!-- Float Action Button -->
    <ImageView
        android:id="@+id/add_note"
        android:layout_width="@dimen/_40sdp"
        android:layout_height="@dimen/_40sdp"
        android:layout_margin="@dimen/_16sdp"
        android:layout_marginEnd="152dp"
        android:layout_marginBottom="68dp"
        android:background="@drawable/fab_background"   // circle background
        android:contentDescription="@string/fab"
        android:padding="@dimen/_10sdp"
        android:src="@drawable/ic_add" // plus icon
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />
```
---

### Activity Create Note
```
Path : app/src/main/res/layout/activity_create_note.xml
```
![1_2_3_4and5-preview](https://github.com/moha-b/Notice/assets/73842931/066f86ce-118b-4014-a104-073fbf4e0d93)

1 - nothing new

2 - we will find this view consist of circle and done icon
```xml
<ImageView
     android:id="@+id/done_button"
     android:layout_width="@dimen/_20sdp"
     android:layout_height="@dimen/_20sdp"
     android:layout_marginTop="@dimen/_16sdp"
     android:layout_marginEnd="@dimen/_16sdp"
     android:background="@drawable/done_background" // the circle 
     android:contentDescription="@string/done"
     android:padding="@dimen/_5sdp"
     android:src="@drawable/ic_done" // the done icon
     app:layout_constraintBottom_toBottomOf="@+id/back_button"
     app:layout_constraintEnd_toEndOf="parent"
     app:layout_constraintTop_toTopOf="@+id/back_button"
     app:layout_constraintVertical_bias="0.86"
     app:tint="@color/icon" /> // the color
```

3, 4 - nothing new

5 - u will find this in the activity_create_note.xml
```xml
 <include layout="@layout/bottom_sheet_layout" />
```
we use `<include/>` tag to put a whole view in another view. so if u wanna see this bottom sheet layout go to app/src/main/res/layout/bottom_sheet_layout.xml

so if u notice in the ** activity_create_note.xml** file we use `CoordinatorLayout` within `ConstraintLayout` within our view (1,2,3,4) exept 5 it's in the `CoordinatorLayout` take a look :
but why we use it why we didn't use `ConstraintLayout` just one time ?
for makeing the xml file look readable
```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/primary" // give it color
    tools:context=".activities.CreateNote">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:animateLayoutChanges="true"> // no need for it
           <!-- Back Button -->
           1
           <!-- Done Button -->
           2 
           <!-- Note Title -->
           3
           <!-- Time & Date -->
           3
           <!-- Note Content -->
           4
        </androidx.constraintlayout.widget.ConstraintLayout>

    <!-- Bottom Sheet -->
    <include layout="@layout/bottom_sheet_layout" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
---

### Note layout
```
Path : app/src/main/res/layout/note_layout.xml
```
consist of 3 views one for the title and the content and the time

---

### Bottom Sheet
```
Path : app/src/main/res/layout/bottom_sheet_layout.xml
```
![Untitled-preview](https://github.com/moha-b/Notice/assets/73842931/4a16e14d-6269-4ee7-a860-09bd816eb149)

here we have just 2 views and these 2 inside a vertical LinearLayout

1 - regular view
```xml
 <View
        android:id="@+id/note_color"
        android:layout_width="@dimen/_60sdp"
        android:layout_height="@dimen/_10sdp"
        android:layout_marginTop="@dimen/_10sdp"
        android:background="@drawable/bottom_sheet_shape" // this line what gives it the shape
        android:layout_gravity="center" // to center the view 
        app:layout_constraintTop_toTopOf="parent" />
```
2 - horizontally LinearLayout : it's containe the 5 colors 
```xml
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="@dimen/_16sdp"
        android:layout_gravity="center_vertical"
        android:layout_marginStart="@dimen/_16sdp"
        android:paddingBottom="@dimen/_10sdp"
        android:baselineAligned="false">
         <!-- FrameLayout : we use it just to make the xml file readable -->
        <FrameLayout
            android:layout_width="0dp" // take the size u need
            android:layout_height="wrap_content"
            android:layout_weight="1"  // till here it will contain 1 view
            >
            <ImageView
                android:id="@+id/im_color_1"
                android:layout_width="@dimen/_35sdp"
                android:layout_height="@dimen/_35sdp"
                android:background="@drawable/background_note_color_1" // here is color circle
                app:layout_constraintEnd_toStartOf="@+id/im_color_2"
                app:layout_constraintStart_toStartOf="parent"
                android:contentDescription="@string/color_button"
                android:src="@drawable/ic_done" // done icon
                android:padding="@dimen/_9sdp"
                app:tint="@color/white" // color for done icon
                app:layout_constraintTop_toBottomOf="@+id/view" />
        </FrameLayout>
```

## Database Section

We use [Room Database](https://developer.android.com/training/data-storage/room) to create our database we have 4 main steps below or u can watch a [tutorial](https://www.youtube.com/watch?v=y30EfQQiOSM&list=PLXjbGq0ERjFq5Y3vEK1v0ic5oEAqmpHa7&index=3).

1 - Import Room dependencies

go to -> app/build.gradle in the dependencies section add 
```gradle    
   def room_version = "2.5.1"
   
   // Room Database
   implementation "androidx.room:room-runtime:$room_version"
   annotationProcessor "androidx.room:room-compiler:$room_version"
```
2 - Create the model class 

model class it's a regular class like this 
```java
public class Note {
   private int id;
   private String title;
}
```
we annotate this class as an Entity we give it a name "notes". and also annotate the variables within this class 
```java
@Entity(tableName = "notes")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;
}
```
and here is the whole class (didn't include the setters & getters)
```java
@Entity(tableName = "notes")
public class Note implements Serializable {

    // Primary key field with auto-generation
    @PrimaryKey(autoGenerate = true)
    private int id;

    // Column field for the note title
    @ColumnInfo(name = "title")
    private String title;

    // Column field for the note content
    @ColumnInfo(name = "content")
    private String content;

    // Column field for the note date
    @ColumnInfo(name = "date")
    private String date;

    // Column field for the note color
    @ColumnInfo(name = "color")
    private String color;
}
```
3 - Create Dao

`Dao` : is the Data Access Object with Dao we can access our database. it's and `INTERFACE` class anotated with `@Dao` anotation 
why it's `INTERFACE` ? : this is the best explination i found [stackoverflow](https://stackoverflow.com/a/23562084/19270622) or u can ask chatgpt
```java
@Dao
public interface NoteDao {}
```
now we create our method
```java
@Dao
public interface NoteDao {

    // Insert a Note object into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    // Update a Note object in the database
    @Update
    void update(Note note);

    // Delete a Note object from the database
    @Delete
    void delete(Note note);

    // Retrieve all Note objects from the "notes" table
    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    // Retrieve a Note object from the "notes" table based on its ID
    @Query("SELECT * FROM notes WHERE id = :noteId")
    Note getNoteById(int noteId);
}
```
`@Insert(onConflict = OnConflictStrategy.REPLACE)` : This annotation is used to define an insert operation, it should be replaced with the new one.

4 - Create the Database

this setup is mandatory to create a `room database` 
```java
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    // Declare an abstract method to retrieve the DAO (Data Access Object)
    public abstract NoteDao dao();

    // Singleton pattern to ensure only one instance of the database is created

    // Declare a volatile instance variable to ensure visibility across threads
    private static volatile NoteDatabase instance;

    // Create a synchronized method to get the instance of the database
    public static synchronized NoteDatabase getInstance(Context context) {
        // Check if the instance is null
        if (instance == null) {
            // If the instance is null, create a new instance using Room's databaseBuilder method
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        // Return the instance of the database
        return instance;
    }
}
```

## Main Activity Section

```java
 private ActivityResultLauncher<Intent> launcher;
```
By using this launcher object, we can tell our app to start a new activity by passing it an Intent object. An Intent is like a message that tells the app what to do. The launcher object takes care of starting the activity and waits for the result to come back.
```java
   // initialize recycler view with the required properties
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Handle the result in the callback function
                    if (result.getResultCode() == RESULT_OK) {
                        // Result is OK, handle success
                        getNotes();
                    } else {
                        // Result is not OK, handle failure or cancellation
                        // here we print the error message
                        Log.e(TAG, "Error: " + result.getResultCode());
                    }
                });
```

to declare any thing in our ui we need to create object from the same data type like if u have an input field it's called `EditText` if u have text in ui then u need a `TextView`, same with the image u need `ImageView`, same with anything else.

after we declare our objects we need to till this object his `id` each view u gonna use must have an `id` like this
```xml
  <!-- Float Action Button -->
    <ImageView
        android:id="@+id/add_note" // <-- this is the ID
        android:layout_width="@dimen/_40sdp"
        android:layout_height="@dimen/_40sdp"
        android:layout_margin="@dimen/_16sdp"
```
```java
// declare object
 ImageView addNote;
 EditText search;
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         // give it an id
         addNote = findViewById(R.id.add_note);
         search = findViewById(R.id.search); 
   }
```

for the Recycler view
```java
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
```
for the Search this 3 methods it's all generated once u type `new TextWatcher` it will ask for this 3 methods 
```java
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
                     // this function implemented in the adapter class
                    adapter.search(editable.toString());
                }
            }
        });
```
for the part we call the data from the database in `getNote` method

1 - Inside the method, a new class GetNotesTask is defined. This class extends the AsyncTask class, which allows us to perform background tasks (like accessing the database) without blocking the main user interface thread.

2 - The GetNotesTask class overrides two methods: doInBackground() and onPostExecute(). These methods are executed at different stages of the background task.

3 - doInBackground() is the method where the actual database operation takes place. It runs in the background thread, separate from the main user interface thread, to avoid freezing or slowing down the app.

4 - Inside the doInBackground() method, the code retrieves all the saved notes from the database. It uses the NoteDatabase class to access the database instance and the associated data access object (DAO). The getAllNotes() method of the DAO returns a List of Note objects.

5 - Once the database operation is complete, the onPostExecute() method is called automatically. This method runs on the main user interface thread, allowing us to update the user interface based on the results of the database operation.

6 - In the onPostExecute() method, the retrieved notes are passed as a parameter (notes). Here, the code performs several operations to update the UI with the new 

7 - Finally, a new instance of the GetNotesTask class is created, and its execute() method is called. This starts the background task, triggering the execution of doInBackground()
```java
private void getNotes() {
    // Get all notes saved in database
    
    // Create a new instance of the GetNotesTask class
    // This class extends AsyncTask to perform the database operation in the background
    @SuppressLint("StaticFieldLeak")
    class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {
        
        @Override
        protected List<Note> doInBackground(Void... voids) {
            // Perform the database operation to get all notes
            // Use the NoteDatabase instance to access the database and the DAO to retrieve the notes
            return NoteDatabase.getInstance(getApplicationContext())
                    .dao().getAllNotes();
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            // This method is executed on the main user interface thread after the database operation is complete
            
            // Clear the noteList before adding the retrieved notes
            noteList.clear();
            
            // Add the retrieved notes to the noteList
            noteList.addAll(notes);
            
            // Notify the adapter that the data set has changed
            // This will update the RecyclerView with the new notes
            adapter.notifyDataSetChanged();
            
            // Scroll the RecyclerView to the top position
            notesRecyclerView.smoothScrollToPosition(0);
            notesRecyclerView.smoothScrollToPosition(0);
        }
    }
    
    // Create an instance of GetNotesTask and execute it
    new GetNotesTask().execute();
}
```
for the audio watch this [tutorial](https://youtu.be/C_Ka7cKwXW0)

for the NoteListener u will notice that there is an implements beside the `MainActivity` class
```java
public class MainActivity extends AppCompatActivity implements NoteListener  // <-- this part here{}
```
this is our NoteListener it's an `INTERFACE` we can create methods and implemented somewhere else. in our case we implement the `onNoteClicked` method in the `MainActivity.class`. but why we make all this ??

because we need to open the note the user gonna click on it, after implement the method in the `MainActivity.class` pass it to the adapter to preform the click action

```java
public interface NoteListener {
    void onNoteClicked(Note note, int position);
}
```
here is the implementation
```java
    @Override
    public void onNoteClicked(Note note, int position) {
        Intent intent = new Intent(getApplicationContext(), CreateNote.class);
        intent.putExtra("isView", true);
        intent.putExtra("note", note);
        launcher.launch(intent);
    }
```
## Create Activity Section

see the code nothing new the code is commented

## Adapter Section

basic steps for the `Adapter`
[arabic tutorial](https://youtu.be/eLVd0kuLeoI) (from 2:40)
[english tutorial](https://youtu.be/18VcnYN5_LM) (from 4:00)

see the code it's commented
