package com.makeitvsolo.hostels.exception;

import java.text.MessageFormat;

public final class HostelAlreadyExistsException extends HostelsException {

    public HostelAlreadyExistsException(String name) {
        super(MessageFormat.format("Hostel {0} already exists", name));
    }
}
