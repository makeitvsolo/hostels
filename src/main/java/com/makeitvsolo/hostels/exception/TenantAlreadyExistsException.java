package com.makeitvsolo.hostels.exception;

import com.makeitvsolo.hostels.common.exception.HostelsException;

import java.text.MessageFormat;

public final class TenantAlreadyExistsException extends HostelsException {

    public TenantAlreadyExistsException(String name) {
        super(MessageFormat.format("Tenant already exists", name));
    }
}
