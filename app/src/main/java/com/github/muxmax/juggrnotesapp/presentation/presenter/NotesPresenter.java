package com.github.muxmax.juggrnotesapp.presentation.presenter;

import android.os.Bundle;

import com.github.muxmax.juggrnotesapp.domain.model.Note;

import java.util.List;

/**
 * This presenter communicates with a View that is able to show {@link Note}s.
 */
public class NotesPresenter {

    private NotesView view;

    /**
     * Should be called when a {@link NotesView} is created.
     *
     * @param view               The view that is created.
     * @param savedInstanceState A bundle that may contain a saved instance state.
     */
    public void onCreate(NotesView view, Bundle savedInstanceState) {

    }

    /**
     * Should be called when a {@link NotesView} has to save its state as it will be paused.
     *
     * @param outState The bundle the state can be saved in.
     */
    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * Should be called when an action took place to add a new {@link Note}.
     */
    public void onAddNote() {

    }

    /**
     * Should be called when a note was long pressed on.
     */
    public void onPressedNote() {

    }

    /**
     * Should be called when the notes list representation has been scrolled and a new position has
     * been reached.
     *
     * @param position a position of the scrolled notes list representation.
     */
    public void onScrolledNotes(int position) {

    }

    /**
     * An interface of a view that is able to display {@link Note}s. Needs to be implemented by a
     * view, when it wants to use this presenter.
     */
    public interface NotesView {

        /**
         * Display the given list of {@link Note}s. If a list is already displayed it should update
         * its content to the given one.
         *
         * @param notes A list of {@link Note}s to be displayed.
         */
        void displayNotes(List<Note> notes);

        /**
         * Get the display position of the notes list representation in the view.
         *
         * @return The position.
         */
        int getDisplayPosition();

        /**
         * Set the display position of the notes list representation in the view.
         *
         * @param position A position to set in the view.
         */
        void setDisplayPosition(int position);

    }
}
