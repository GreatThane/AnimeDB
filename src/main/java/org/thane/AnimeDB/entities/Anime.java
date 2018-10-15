package org.thane.AnimeDB.entities;

import javax.persistence.*;

@Entity
public class Anime {


    @Id
    private int id;
    private String name;
    private String type;

    public Anime() {
    }

    public Anime(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Anime(WatchListEntry watchListEntry) {
        this.id = watchListEntry.getId();
        this.name = watchListEntry.getName();
        this.type = watchListEntry.getType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
