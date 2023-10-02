package com.makeitvsolo.hostels.service.exception.member;

import com.makeitvsolo.hostels.common.exception.HostelsException;

public final class PasswordsAreNotMatchesException extends HostelsException {

    public PasswordsAreNotMatchesException() {
        super("Invalid password");
    }
}
