package org.thane.AnimeDB.exceptions;

public class AccountException extends Exception {

    private String username;
    private int id;

    public AccountException(String username) {
        super("Account " + username + " is now privated!");
        this.username = username;
    }

    public AccountException(int id) {
        this.id = id;
    }

    public AccountException(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
