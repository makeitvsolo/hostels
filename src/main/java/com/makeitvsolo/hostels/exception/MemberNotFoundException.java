package com.makeitvsolo.hostels.exception;

import com.makeitvsolo.hostels.common.exception.HostelsException;

import java.text.MessageFormat;

public final class MemberNotFoundException extends HostelsException {

    public MemberNotFoundException(String name) {
        super(MessageFormat.format("Member {0} not found", name));
    }

    public MemberNotFoundException() {
        super("Member not found");
    }
}
