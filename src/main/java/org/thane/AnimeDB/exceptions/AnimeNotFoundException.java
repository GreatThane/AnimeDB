package org.thane.AnimeDB.exceptions;

public class AnimeNotFoundException extends AnimeException {

    public AnimeNotFoundException(int id) {
        super(id);
    }

    public AnimeNotFoundException(String name) {
        super(name);
    }

    public AnimeNotFoundException(String name, int id) {
        super(name, id);
    }
}
