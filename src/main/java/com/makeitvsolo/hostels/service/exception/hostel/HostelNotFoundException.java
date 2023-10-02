package com.makeitvsolo.hostels.service.exception.hostel;

import com.makeitvsolo.hostels.common.exception.HostelsException;

public final class HostelNotFoundException extends HostelsException {

    public HostelNotFoundException() {
        super("Hostel not found");
    }
}
