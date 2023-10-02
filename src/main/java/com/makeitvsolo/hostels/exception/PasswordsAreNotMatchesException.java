package com.makeitvsolo.hostels.exception;

import com.makeitvsolo.hostels.common.exception.HostelsException;

public final class PasswordsAreNotMatchesException extends HostelsException {

    public PasswordsAreNotMatchesException() {
        super("Invalid password");
    }
}
