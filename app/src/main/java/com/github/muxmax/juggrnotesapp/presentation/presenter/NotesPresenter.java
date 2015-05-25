package com.github.muxmax.juggrnotesapp.presentation.presenter;

import android.os.Bundle;

import com.github.muxmax.juggrnotesapp.domain.interactors.GetAllNotes;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.github.muxmax.juggrnotesapp.presentation.util.BundleArguments;

import java.util.List;

import javax.inject.Inject;

/**
 * This presenter communicates with a View that is able to show {@link Note}s.
 */
public class NotesPresenter implements GetAllNotes.Callback {

    @Inject GetAllNotes getAllNotes;

    private NotesView view;

    private int position;

    /**
     * Should be called when a {@link NotesView} is created.
     *
     * @param view               The view that is created.
     * @param savedInstanceState A bundle that may contain a saved instance state.
     */
    public void onCreate(NotesView view, Bundle savedInstanceState) {
        this.view = view;
        tryLoadingViewState(savedInstanceState);
    }

    private void tryLoadingViewState(Bundle savedInstanceState) {
        position = savedInstanceState == null ?
                0 :
                savedInstanceState.getInt(BundleArguments.SCROLL_POSITION, 0);
        getAllNotes.execute(this);
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

    @Override
    public void onSuccess(List<Note> notes) {
        view.displayNotes(notes);
        view.setDisplayPosition(position);
    }

    @Override
    public void onError() {
        view.displayLoadingError();
    }

    /**
     * An interface of a view that is able to display {@link Note}s. Needs to be implemented by a
     * view, when it wants to use this presenter.
     */
    public interface NotesView {

        /**
         * Displays the given list of {@link Note}s. If a list is already displayed it should
         * update
         * its content to the given one.
         *
         * @param notes A list of {@link Note}s to be displayed.
         */
        void displayNotes(List<Note> notes);

        /**
         * Displays an error message to the user as something went wrong during loading {@link
         * Note}s
         */
        void displayLoadingError();

        /**
         * Gets the display position of the notes list representation in the view.
         *
         * @return The position.
         */
        int getDisplayPosition();

        /**
         * Sets the display position of the notes list representation in the view.
         *
         * @param position A position to set in the view.
         */
        void setDisplayPosition(int position);
    }
}
