package com.makeitvsolo.hostels.service.exception.hostel;

import com.makeitvsolo.hostels.common.exception.HostelsException;

public final class TenantNotFoundException extends HostelsException {

    public TenantNotFoundException() {
        super("Tenant not found");
    }
}
