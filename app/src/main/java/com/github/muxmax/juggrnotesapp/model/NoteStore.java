package com.github.muxmax.juggrnotesapp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This interface describes a model layer component that manages providing {@link
 * com.github.muxmax.juggrnotesapp.model.Note} instances for the
 * application.
 */
public class NoteStore {

    private static NoteStore INSTANCE;

    private Map<Long, Note> notesMap = new HashMap<>();

    private NoteStore() {
    }

    public static NoteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoteStore();
        }
        return INSTANCE;
    }

    /**
     * Save a {@link com.github.muxmax.juggrnotesapp.model.Note} for the given input arguments.
     *
     * @param note The id of the {@link com.github.muxmax.juggrnotesapp.model.Note}. If it is null
     *             the store should manage providing a valid id.
     * @return true, if the note was not empty and therefore could be save. false, otherwise.
     */
    public boolean save(Note note) {
        if (notEmpty(note)) {
            notesMap.put(note.getId(), note);
            return true;
        } else {
            return false;
        }
    }

    private boolean notEmpty(Note note) {
        return note.getImagePath() != null || !note.getTitle().isEmpty() ||
                !note.getContent().isEmpty();
    }

    /**
     * Provide a {@link com.github.muxmax.juggrnotesapp.model.Note} that already exists with the
     * given id or provide a new one.
     *
     * @param noteId An id that might be null, 0, or any UUID.
     * @return A {@link com.github.muxmax.juggrnotesapp.model.Note} that may exist already or a new
     * {@link com.github.muxmax.juggrnotesapp.model.Note}.
     */
    public Note provide(Long noteId) {
        if (noteId == null || noteId == 0) {

            Note newNote = new Note();
            notesMap.put(newNote.getId(), newNote);

            return newNote;
        } else {
            return notesMap.get(noteId);
        }
    }

}
