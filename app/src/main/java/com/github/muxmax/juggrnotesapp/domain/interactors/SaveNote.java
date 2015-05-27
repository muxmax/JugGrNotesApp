package com.github.muxmax.juggrnotesapp.domain.interactors;

import com.github.muxmax.juggrnotesapp.domain.model.Note;

/**
 * This interactor describes the use case of saving a single {@link Note}. If the {@link Note} does
 * not yet exist in the data layer it already does is unimportant.
 */
public interface SaveNote {

    /**
     * Executes the use case on the given {@link Note}.
     *
     * @param note Any {@link Note} that needs to be added or updated in the data layer.
     */
    void execute(Note note);

}
