package com.pedrobacchini.imdbcardgame.application.exception;

public class NotFoundException extends NoStacktraceException {

    public NotFoundException(final String message) {
        super(message);
    }

}
