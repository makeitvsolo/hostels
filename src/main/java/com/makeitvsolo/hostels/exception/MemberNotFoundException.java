package com.makeitvsolo.hostels.exception;

import java.text.MessageFormat;

public final class MemberNotFoundException extends HostelsException {

    public MemberNotFoundException(String name) {
        super(MessageFormat.format("Member {0} not found", name));
    }
}