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
the path : app/src/main/res/layout/activity_main.xml
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
        android:background="@drawable/background_search" // here we give the LinearLayout a background how? later..
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
the path : app/src/main/res/layout/activity_create_note.xml
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


## Database Section

## Main Activity Section

## Create Activity Section

## Adapter Section
