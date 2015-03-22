package com.github.muxmax.juggrnotesapp.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.util.BundleArguments;

/**
 * An activity that shows detail information on a given note.
 */
public class NoteDetailActivity extends ActionBarActivity {

    // model state
    private Long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (stateCouldBeLoaded(savedInstanceState)) {

            initializeView();

        } else {
            handleLoadingError();
        }
    }

    /**
     * Initialize all views of the activity.
     */
    private void initializeView() {
        setContentView(R.layout.note_detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * Loads the activity arguments either from the given savedInstancesState or the intent this
     * activity was created for.
     *
     * @param savedInstancesState A {@link android.os.Bundle} that might contain a saved activity
     *                            state.
     * @return true, if the activity state could be loaded. false, otherwise.
     */
    private boolean stateCouldBeLoaded(Bundle savedInstancesState) {
        if (savedInstancesState == null) {
            noteId = getIntent().getLongExtra(BundleArguments.NOTE_ID, 0);
        } else {
            noteId = savedInstancesState.getLong(BundleArguments.NOTE_ID);
        }
        return noteId != null;
    }

    /**
     * Handle the error when the activity state could not be loaded correctly.
     */
    private void handleLoadingError() {
        Log.e(getClass().getName(),
                "The activity was not provided with the necessary arguments, " +
                        "or the state could not be restored correctly after pausing.");
        finish();
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
        }

        return super.onOptionsItemSelected(item);
    }
}
