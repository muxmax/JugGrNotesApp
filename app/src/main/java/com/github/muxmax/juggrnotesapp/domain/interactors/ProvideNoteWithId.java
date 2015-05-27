package com.github.muxmax.juggrnotesapp.domain.interactors;

import com.github.muxmax.juggrnotesapp.domain.model.Note;

/**
 * This interactor describes the use case of getting a specific {@link Note} that has the given id.
 * This means, if such a {@link Note} exists in the data layer it is loaded. otherwise a new one is
 * created and returned (but not yet persisted.
 */
public interface ProvideNoteWithId {

    /**
     * Executes the use case and notify the callback on the result state.
     *
     * @param noteId   The id of a note to be provided. If the id equal to 0 than a new one will be
     *                 created.
     * @param callback The callback to be notified.
     */
    void execute(Long noteId, Callback callback);

    /**
     * A Callback that is notified on the results of this use case.
     */
    interface Callback {
        /**
         * All {@link Note}s could be retrieved.
         *
         * @param note The note that was loaded from the data layer or created newly.
         */
        void onSuccess(Note note);
    }
}
