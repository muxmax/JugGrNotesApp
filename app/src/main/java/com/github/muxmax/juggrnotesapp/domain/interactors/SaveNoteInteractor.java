package com.github.muxmax.juggrnotesapp.domain.interactors;

import com.github.muxmax.juggrnotesapp.data.NoteRepository;
import com.github.muxmax.juggrnotesapp.domain.model.Note;

import javax.inject.Inject;

/**
 * Provides the use case implementation of {@link SaveNote}.
 */
public class SaveNoteInteractor implements SaveNote {

    @Inject NoteRepository noteRepository;

    @Override
    public void execute(Note note) {
        noteRepository.addOrUpdate(note);
    }
}
