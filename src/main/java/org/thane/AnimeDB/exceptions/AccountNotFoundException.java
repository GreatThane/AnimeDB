package org.thane.AnimeDB.exceptions;

public class AccountNotFoundException extends AccountException {
    public AccountNotFoundException(String username) {
        super(username);
    }

    public AccountNotFoundException(int id) {
        super(id);
    }

    public AccountNotFoundException(String username, int id) {
        super(username, id);
    }
}
