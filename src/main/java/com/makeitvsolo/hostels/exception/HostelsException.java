package com.makeitvsolo.hostels.exception;

public abstract class HostelsException extends RuntimeException {

    protected HostelsException(String message) {
        super(message);
    }

    protected HostelsException(Exception ex) {
        super(ex);
    }
}
