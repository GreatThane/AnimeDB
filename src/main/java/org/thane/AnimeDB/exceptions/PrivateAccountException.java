package org.thane.AnimeDB.exceptions;

public class PrivateAccountException extends AccountException {
    public PrivateAccountException(String username) {
        super(username);
    }
}
