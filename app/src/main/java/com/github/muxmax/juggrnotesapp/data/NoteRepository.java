package com.github.muxmax.juggrnotesapp.data;

import com.github.muxmax.juggrnotesapp.domain.model.Note;

import java.util.List;

import java8.util.Optional;
import java8.util.function.Predicate;

/**
 * A Repository of {@link Note}s. The storing of the note is specific to the implementation of this
 * interface.
 */
public interface NoteRepository {

    /**
     * Adds or updates the given {@link Note} in the note store.
     *
     * @param note Any note that may or may not exist.
     */
    void addOrUpdate(Note note);

    /**
     * Deletes the given {@link Note} in the database.
     *
     * @param note Any note to be removed from note store.
     * @throws NoteNotFoundException May be thrown, when a {@link Note} shall be deleted that
     *                               doesn't exist in the note store.
     */
    void delete(Note note) throws NoteNotFoundException;

    /**
     * Finds a specific {@link Note} in the store.
     *
     * @param querySpecification A predicate that determines if a {@link Note} is the queried
     *                           {@link
     *                           Note}.
     * @return A {@link Note} wrapped in an {@link Optional}. If any or multiple {@link Note}s could
     * be found that fulfill the given predicate the {@link Optional} is empty.
     */
    Optional<Note> find(Predicate<Note> querySpecification);

    /**
     * Finds specific {@link Note}s that fulfill the given predicate.
     *
     * @param querySpecification A predicate that determines if a {@link Note} is the queried
     *                           {@link
     *                           Note}.
     * @return A list of {@link Note}s that fulfill the given predicate.
     */
    List<Note> findSpecific(Predicate<Note> querySpecification);

    /**
     * Finds all {@link Note}s that exist.
     *
     * @return A list of {@link Note}s
     */
    List<Note> findAll();

}
