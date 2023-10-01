package com.makeitvsolo.hostels.exception;

public final class PasswordsAreNotMatchesException extends HostelsException {

    public PasswordsAreNotMatchesException() {
        super("Invalid password");
    }
}
