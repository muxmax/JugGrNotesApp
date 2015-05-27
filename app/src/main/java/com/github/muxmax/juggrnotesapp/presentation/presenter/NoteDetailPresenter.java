package com.github.muxmax.juggrnotesapp.presentation.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.github.muxmax.juggrnotesapp.domain.interactors.ProvideNoteWithId;
import com.github.muxmax.juggrnotesapp.domain.interactors.SaveNote;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.github.muxmax.juggrnotesapp.presentation.util.BundleArguments;

import javax.inject.Inject;

/**
 * This presenter communicates with a View that is able to show the detail information of a single
 * {@link Note}. It represents all logic necessary to display and manipulate a {@link Note}.
 */
public class NoteDetailPresenter implements ProvideNoteWithId.Callback {

    @Inject ProvideNoteWithId provideNoteWithId;
    @Inject SaveNote saveNote;
    private NoteDetailView view;

    public void onCreate(NoteDetailView view, Bundle savedInstancesState, Intent intent) {
        this.view = view;
        tryLoadingViewState(savedInstancesState, intent);
    }

    private void tryLoadingViewState(Bundle savedInstancesState, Intent intent) {
        Long noteId;
        if (savedInstancesState == null) {
            noteId = intent.getLongExtra(BundleArguments.NOTE_ID, 0);
        } else {
            noteId = savedInstancesState.getLong(BundleArguments.NOTE_ID);
        }
        provideNoteWithId.execute(noteId, this);
    }

    public void onPressedTakePhotoAction() {

    }

    public void onPressedChooseColorAction() {

    }

    public void onAddedImage() {
        // make textfield display less lines and show remove image button
    }

    public void onBackAction() {
        Note note = view.getNote();
        if (isEmpty(note)) {
            view.displayEmptyNoteMessage();
        } else {
            saveNote.execute(note);
            view.displaySavedNoteMessage();
        }
    }

    // Validation is a interesting point because the question appears whether
    // to put it in our business rules (Interactors) inside the domain layer or
    // just place it right here in the presenter (presentation layer)
    private boolean isEmpty(Note note) {
        boolean emptyTitle = note.getTitle() == null || note.getTitle().isEmpty();
        boolean emptyContent = note.getContent() == null || note.getContent().isEmpty();
        boolean noImage = note.getImagePath() == null || note.getImagePath().isEmpty();
        return emptyTitle && emptyContent && noImage;
    }

    @Override
    public void onSuccess(Note note) {
        view.displayNote(note);
    }

    /**
     * An interface of a view that is able to display detail information on a {@link Note}. Needs
     * to
     * be implemented by a
     * view, when it wants to use this presenter.
     */
    public interface NoteDetailView {

        /**
         * Gets the {@link Note} that is currently displayed by a view.
         *
         * @return The displayed {@link Note}.
         */
        Note getNote();

        /**
         * Displays a single {@link Note} in the view.
         *
         * @param note The {@link Note} to be displayed.
         */
        void displayNote(Note note);

        /**
         * Displays a messages that tells the user that an empty note cannot be saved.
         */
        void displayEmptyNoteMessage();

        /**
         * Displays a message that the note has been saved.
         */
        void displaySavedNoteMessage();
    }
}
