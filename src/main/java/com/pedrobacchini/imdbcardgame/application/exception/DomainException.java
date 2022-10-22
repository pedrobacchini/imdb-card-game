package com.pedrobacchini.imdbcardgame.application.exception;

public class DomainException extends NoStacktraceException {

    public DomainException(final String message) {
        super(message);
    }

}
