package com.jholguin.converter.exception;

public class ConversionException extends Exception {

    public ConversionException(final String message) {
        super(message);
    }

    public ConversionException(final String message, final Exception ex) {
        super(message, ex);
    }
}
