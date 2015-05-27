package com.github.muxmax.juggrnotesapp.data;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.github.muxmax.juggrnotesapp.presentation.di.Application;

import java.util.ArrayList;
import java.util.List;

import java8.util.Optional;
import java8.util.function.Consumer;
import java8.util.function.Predicate;
import java8.util.stream.Collectors;

import static java8.util.stream.StreamSupport.stream;

/**
 * A specific implementation that provides persistence and caching of model data from an Sqlite
 * database.
 */
public class SqliteDbNoteRepository implements NoteRepository {

    private List<Note> notesCache = new ArrayList<>();

    public SqliteDbNoteRepository(Application application) {
        ActiveAndroid.initialize(application);
    }

    @Override
    public void addOrUpdate(Note note) {
        map(note).save();
        if (notesCache.contains(note)) {
            notesCache.remove(note);
        }
        notesCache.add(note);
    }

    @Override
    public void delete(Note note) throws NoteNotFoundException {
        NoteEntity entity = map(note);
        notesCache.remove(note);
        if (entity.getId() == null) {
            throw new NoteNotFoundException(
                    "The Note with ID = " + note.getId() + "and title '" + note.getTitle() +
                            "' has no entity in the database. Removed it from cache.");
        } else {
            entity.delete();
        }
    }

    @Override
    public Optional<Note> find(Predicate<Note> querySpecification) {
        List<Note> foundNotes = findSpecific(querySpecification);
        Optional<Note> noteOptional = foundNotes.size() == 1 ?
                Optional.of(foundNotes.get(0)) :
                Optional.<Note>empty();
        return noteOptional;
    }

    @Override
    public List<Note> findSpecific(Predicate<Note> querySpecification) {
        if (notesCache.isEmpty()) {
            stream(finaAllNotesInDb()).forEach(new Consumer<NoteEntity>() {
                @Override
                public void accept(NoteEntity noteEntity) {
                    notesCache.add(map(noteEntity));
                }
            });
        }

        if (querySpecification == null) {
            return notesCache;
        } else {
            return stream(notesCache).filter(querySpecification).collect(
                    Collectors.<Note>toList());
        }

    }

    @Override
    public List<Note> findAll() {
        return findSpecific(null);
    }

    private List<NoteEntity> finaAllNotesInDb() {
        return new Select()
                .from(NoteEntity.class)
                .execute();
    }

    private Note map(NoteEntity entity) {
        Note note = new Note();
        note.setTitle(entity.getTitle());
        note.setContent(entity.getContent());
        note.setColor(entity.getColor());
        note.setImagePath(entity.getImagePath());
        return note;
    }

    private NoteEntity map(Note note) {
        NoteEntity entity = new NoteEntity();
        entity.setTitle(note.getTitle());
        entity.setContent(note.getContent());
        entity.setColor(note.getColor());
        entity.setImagePath(note.getImagePath());
        return entity;
    }
}
