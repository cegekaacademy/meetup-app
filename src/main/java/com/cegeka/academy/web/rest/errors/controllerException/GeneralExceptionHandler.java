package com.cegeka.academy.web.rest.errors.controllerException;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GeneralExceptionHandler extends HttpResponseExceptionHandler {

    public ResponseEntity<ErrorResponse> handleExpiredEvent(Event event) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("idEvent: " + event.getId());
        detailsList.add("eventName: " + event.getName());
        return getErrorResponseEntity(
                ErrorCode.EVENT_EXPIRED.getMessage(),
                ErrorCode.EVENT_EXPIRED.getCode(),
                detailsList,
                HttpStatus.GONE);
    }

    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessEvent(Event event) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("idEvent: " + event.getId());
        detailsList.add("eventName: " + event.getName());
        return getErrorResponseEntity(
                ErrorCode.UNAUTHORIZED_ACCESS.getMessage(),
                ErrorCode.UNAUTHORIZED_ACCESS.getCode(),
                detailsList,
                HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessInvitation(Invitation invitation) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("idInvitation: " + invitation.getId());
        detailsList.add("invitationDescription: " + invitation.getDescription());
        return getErrorResponseEntity(
                ErrorCode.UNAUTHORIZED_ACCESS.getMessage(),
                ErrorCode.UNAUTHORIZED_ACCESS.getCode(),
                detailsList,
                HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ErrorResponse> handleNotFoundInvitation(Long id) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("idInvitation: " + id);
        return getErrorResponseEntity(
                ErrorCode.NOT_FOUND.getMessage(),
                ErrorCode.NOT_FOUND.getCode(),
                detailsList,
                HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ErrorResponse> handleNotFoundEvent(Long id) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("idEvent: " + id);
        return getErrorResponseEntity(
                ErrorCode.NOT_FOUND.getMessage(),
                ErrorCode.NOT_FOUND.getCode(),
                detailsList,
                HttpStatus.NOT_FOUND);
    }
}
