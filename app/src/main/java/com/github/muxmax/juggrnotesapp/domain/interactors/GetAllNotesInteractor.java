package com.github.muxmax.juggrnotesapp.domain.interactors;

import com.github.muxmax.juggrnotesapp.data.NoteStore;

import javax.inject.Inject;

/**
 * Provides the use case implementation of {@link GetAllNotes}.
 */
public class GetAllNotesInteractor implements GetAllNotes {

    @Inject NoteStore store;

    @Override
    public void execute(Callback callback) {
        // currently the implementation works synchronously,
        // means when there are long lasting operations the UI thread is blocked.
        // Later this should be executed in another thread.
        callback.onSuccess(store.findAll());
    }
}
