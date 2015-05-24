package com.github.muxmax.juggrnotesapp.domain.interactors;

import com.github.muxmax.juggrnotesapp.domain.model.Note;

import java.util.List;

/**
 * This interactor describes the use case of getting all {@link Note}s.
 */
public interface GetAllNotes {

    /**
     * Executes the use case and notify the callback on the result state.
     *
     * @param callback The callback to be notified.
     */
    void execute(Callback callback);

    /**
     * A Callback that is notified on the results of this use case.
     */
    interface Callback {
        /**
         * All {@link Note}s could be retrieved.
         *
         * @param notes The notes that were loaded from the data layer.
         */
        void onSuccess(List<Note> notes);

        /**
         * Something when wrong getting all {@link Note}s.
         */
        void onError();
    }
}
