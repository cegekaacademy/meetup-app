package com.cegeka.academy.web.rest.errors.controllerException;

public enum ErrorCode {

    EVENT_EXPIRED(410, "The event is no longer available"),
    UNAUTHORIZED_ACCESS(401, "You have no right to change the object"),
    NOT_FOUND(404, "The searched object does not exist"),
    INVALID_ARGUMENT(400, "Invalid parameter value in the request");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
