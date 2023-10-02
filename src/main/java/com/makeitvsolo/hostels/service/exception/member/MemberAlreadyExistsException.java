package com.makeitvsolo.hostels.service.exception.member;

import com.makeitvsolo.hostels.common.exception.HostelsException;

import java.text.MessageFormat;

public final class MemberAlreadyExistsException extends HostelsException {

    public MemberAlreadyExistsException(String name) {
        super(MessageFormat.format("Member name {0} already taken", name));
    }
}
