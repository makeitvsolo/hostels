package com.makeitvsolo.hostels.exception;

public final class HostelNotFoundException extends HostelsException {

    public HostelNotFoundException() {
        super("Hostel not found");
    }
}
