package com.github.muxmax.juggrnotesapp.view;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.model.Note;
import com.github.muxmax.juggrnotesapp.model.NoteStore;
import com.github.muxmax.juggrnotesapp.util.BundleArguments;
import com.github.muxmax.juggrnotesapp.util.NavigationUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * An activity that shows detail information on a given note.
 */
public class NoteDetailActivity extends ActionBarActivity {

    // model state
    private Note note;

    // view
    private View rootView;
    private ImageView imageView;
    private ImageButton buttonDeletePhoto;
    private EditText editTextTitle;
    private EditText editTextContent;

    // view state
    private Uri capturedImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadState(savedInstanceState);
        initializeView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NavigationUtils.RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            if (!NavigationUtils.isFromCamera(data) && data == null) {
                onErrorTakingAPhoto();
            } else {
                onLoadTakenPhoto(data);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_take_photo) {
            onActionTakeAPhoto();
        } else if (id == R.id.action_choose_color) {
            onActionChooseColor();
        } else if (id == android.R.id.home) {
            onSaveNote();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onSaveNote();
    }

    private void onSaveNote() {
        note.setTitle(editTextTitle.getText().toString());
        note.setContent(editTextContent.getText().toString());

        if (NoteStore.getInstance().save(note)) {
            Toast.makeText(this, R.string.saved_note, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.did_not_save_note_as_empty, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialize all views of the activity.
     */
    private void initializeView() {
        setContentView(R.layout.note_detail_activity);

        rootView = findViewById(R.id.rootView);
        rootView.setBackgroundColor(note.getColor());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imageView = (ImageView) findViewById(R.id.imageView);
        if (note.getImagePath() != null) {
            loadImageIntoView(note.getImagePath());
        }
        buttonDeletePhoto = (ImageButton) findViewById(R.id.buttonDeletePhoto);
        buttonDeletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
                editTextContent.setMaxLines(Integer.MAX_VALUE);
                buttonDeletePhoto.setVisibility(View.GONE);
            }
        });

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTitle.setText(note.getTitle());
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        editTextContent.setText(note.getContent());
    }

    /**
     * Loads the activity arguments either from the given savedInstancesState or the intent this
     * activity was created for.
     *
     * @param savedInstancesState A {@link android.os.Bundle} that might contain a saved activity
     *                            state.
     */
    private void loadState(Bundle savedInstancesState) {
        Long noteId;
        if (savedInstancesState == null) {
            noteId = getIntent().getLongExtra(BundleArguments.NOTE_ID, 0);
        } else {
            noteId = savedInstancesState.getLong(BundleArguments.NOTE_ID);
        }
        note = NoteStore.getInstance().provide(noteId);
    }

    /**
     * Should be called to signal that a photo activity should be notified to start.
     */
    private void onActionTakeAPhoto() {
        capturedImageUri = NavigationUtils.goToGalleryOrPhotoApp(this);
    }

    /**
     * Should be called to signal that a color needs to be chosen.
     */
    private void onActionChooseColor() {
        DialogFragment colorChooser = ColorChooserDialogFragment
                .newInstance("red", new ColorChooserDialogFragment.Callback() {
                    @Override
                    public void onChosenColor(int color) {
                        note.setColor(color);
                        rootView.setBackgroundColor(note.getColor());
                    }
                });
        colorChooser.show(getFragmentManager(), "Color Chooser Dialog");
    }

    /**
     * Load the photo from the returned intent into the proper image view.
     *
     * @param data An {@link android.content.Intent} containing data of of the taken photo.
     */
    private void onLoadTakenPhoto(Intent data) {
        String imagePath = NavigationUtils.isFromCamera(data) ?
                capturedImageUri.getPath() :
                NavigationUtils.getPath(data.getData(), this);
        note.setImagePath(imagePath);
        loadImageIntoView(imagePath);
    }

    private void loadImageIntoView(String imagePath) {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point p = new Point(0, 0);
        display.getSize(p);

        Picasso.with(this).load(new File(imagePath))
                // actually we needed here to put in a proportional y value calculated from the image ratio.
                .resize(p.x, p.y)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        editTextContent.setMaxLines(11);
                        buttonDeletePhoto.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    /**
     * Handle the error that occurred while taking a photo.
     */
    private void onErrorTakingAPhoto() {
        Log.e(getClass().getName(),
                "Error: The photo activity could not return an image.");
        Toast.makeText(this, R.string.the_photo_could_not_be_taken, Toast.LENGTH_LONG).show();
    }
}
