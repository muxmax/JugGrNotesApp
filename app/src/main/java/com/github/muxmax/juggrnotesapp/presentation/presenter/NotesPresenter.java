package com.github.muxmax.juggrnotesapp.presentation.presenter;

import com.github.muxmax.juggrnotesapp.domain.interactors.GetAllNotes;
import com.github.muxmax.juggrnotesapp.domain.model.Note;

import java.util.List;

import javax.inject.Inject;

/**
 * This presenter communicates with a View that is able to show {@link Note}s. It represents all
 * logic necessary to display and manage {@link Note}s.
 */
public class NotesPresenter implements GetAllNotes.Callback {

    @Inject GetAllNotes getAllNotes;

    private NotesView view;

    /**
     * Should be called when a {@link NotesView} is created.
     *
     * @param view               The view that is created.
     */
    public void onCreate(NotesView view) {
        this.view = view;
    }

    /**
     * Should be called when the execution of the {@link NotesView} is continued.
     */
    public void onResume() {
        getAllNotes.execute(this);
    }

    /**
     * Should be called when an action took place to add a new {@link Note}.
     */
    public void onPressedAddNoteButton() {
        view.openNoteDetails(0l);
    }

    /**
     * Should be called when a note was long pressed on.
     *
     * @param note The {@link Note} that was pressed.
     */
    public void onPressedNote(Note note) {
        view.openNoteDetails(note.getId());
    }

    @Override
    public void onSuccess(List<Note> notes) {
        view.displayNotes(notes);
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
         * Causes the view to open the detail view for the note.
         *
         * @param noteId An id of a {@link Note} to be displayed in detail.
         */
        void openNoteDetails(long noteId);
    }
}
