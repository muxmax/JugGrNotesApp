package com.github.muxmax.juggrnotesapp.util;

import android.content.Context;
import android.content.Intent;

import com.github.muxmax.juggrnotesapp.view.NoteDetailActivity;

/**
 * A utility class that provides navigation between different app parts. This decouples all views
 * (activities and fragments) from each other.
 */
public class NavigationUtils {

    /**
     * Navigate to the {@link com.github.muxmax.juggrnotesapp.view.NoteDetailActivity}.
     * @param context The context the new activity is started from.
     * @param noteId The id of the note to be viewed.
     */
    public static void goToNoteDetailActivity(Context context, Long noteId) {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(BundleArguments.NOTE_ID, noteId);
        context.startActivity(intent);
    }
}
