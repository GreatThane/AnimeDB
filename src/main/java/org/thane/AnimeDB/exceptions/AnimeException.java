package org.thane.AnimeDB.exceptions;

public class AnimeException extends Exception {
    private Integer id;
    private String name;

    public AnimeException(int id) {
        this.id = id;
    }

    public AnimeException(String name) {
        this.name = name;
    }

    public AnimeException(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasId() {
        return id != null;
    }

    public boolean hasName() {
        return name != null;
    }
}
