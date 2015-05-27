package com.github.muxmax.juggrnotesapp.domain.interactors;

import com.github.muxmax.juggrnotesapp.data.NoteRepository;
import com.github.muxmax.juggrnotesapp.domain.model.Note;

import javax.inject.Inject;

import java8.util.Optional;
import java8.util.function.Predicate;

/**
 * Provides the use case implementation of {@link ProvideNoteWithId}.
 */
public class ProvideNoteWithIdInteractor implements ProvideNoteWithId {

    @Inject NoteRepository noteRepository;

    @Override
    public void execute(final Long noteId, Callback callback) {
        Optional<Note> noteOptional = noteRepository.find(new Predicate<Note>() {
            @Override
            public boolean test(Note note) {
                return note.getId().equals(noteId);
            }
        });

        if (noteOptional.isPresent()) {
            callback.onSuccess(noteOptional.get());
        } else {
            callback.onSuccess(new Note());
        }
    }
}
