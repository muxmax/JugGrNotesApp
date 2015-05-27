package com.github.muxmax.juggrnotesapp.data;

/**
 * Will be thrown, when an entity could not be found in the database.
 */
public class NoteNotFoundException extends Exception {

    public NoteNotFoundException(String message) {
        super(message);
    }
}
