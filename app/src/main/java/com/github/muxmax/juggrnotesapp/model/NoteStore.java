package com.github.muxmax.juggrnotesapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This interface describes a model layer component that manages providing {@link
 * com.github.muxmax.juggrnotesapp.model.Note} instances for the
 * application.
 */
public class NoteStore {

    private static NoteStore INSTANCE;

    private Map<Long, Note> persistedNotesMap = new HashMap<>();
    private Map<Long, Note> mergedNotesMap = new HashMap<>();

    private NoteStore() {

        for (Note note : SampleNotes.generate(20)) {
            persistedNotesMap.put(note.getId(), note);
        }
    }

    public static NoteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoteStore();
        }
        return INSTANCE;
    }

    /**
     * Save in a transient memory, that is not persistent.
     *
     * @param note A {@link com.github.muxmax.juggrnotesapp.model.Note}.
     */
    public void merge(Note note) {
        persistedNotesMap.put(note.getId(), note);
    }

    /**
     * Save a {@link com.github.muxmax.juggrnotesapp.model.Note} if it is not empty.
     *
     * @param note A {@link com.github.muxmax.juggrnotesapp.model.Note}.
     * @return true, if the note was not empty and therefore could be persisted. false, otherwise.
     */
    public boolean persist(Note note) {
        if (notEmpty(note)) {
            persistedNotesMap.put(note.getId(), note);
            mergedNotesMap.remove(note.getId());
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
            mergedNotesMap.put(newNote.getId(), newNote);
            return newNote;

        } else {

            if (mergedNotesMap.containsKey(noteId)) {
                return mergedNotesMap.get(noteId);
            } else {
                return persistedNotesMap.get(noteId);
            }
        }
    }

    /**
     * @return A list of all persisted {@link com.github.muxmax.juggrnotesapp.model.Note}s.
     */
    public List<Note> findAll() {
        return new ArrayList<>(persistedNotesMap.values());
    }
}
