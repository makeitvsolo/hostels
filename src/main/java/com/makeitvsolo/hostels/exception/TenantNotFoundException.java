package com.makeitvsolo.hostels.exception;

public final class TenantNotFoundException extends HostelsException {

    public TenantNotFoundException() {
        super("Tenant not found");
    }
}
