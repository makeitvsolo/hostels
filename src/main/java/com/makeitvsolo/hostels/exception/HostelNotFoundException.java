package com.makeitvsolo.hostels.exception;

import com.makeitvsolo.hostels.common.exception.HostelsException;

public final class HostelNotFoundException extends HostelsException {

    public HostelNotFoundException() {
        super("Hostel not found");
    }
}
