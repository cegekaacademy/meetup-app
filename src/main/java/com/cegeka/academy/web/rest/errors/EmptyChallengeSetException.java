package com.cegeka.academy.web.rest.errors;

public class EmptyChallengeSetException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public EmptyChallengeSetException setMessage(String message) {
        this.message = message;
        return this;
    }
}
