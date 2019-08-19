package com.cegeka.academy.web.rest.errors.controllerException;

import com.cegeka.academy.web.rest.errors.ExpiredObjectException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.UnauthorizedUserException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GeneralExceptionHandler extends HttpResponseExceptionHandler {

    @ExceptionHandler(value = ExpiredObjectException.class)
    public ResponseEntity<ErrorResponse> handleExpiredEvent(ExpiredObjectException e) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("ErrorMessage: " + e.getMessage());
        detailsList.add("ErrorCause: " + e.getCause());
        return getErrorResponseEntity(
                ErrorCode.EVENT_EXPIRED.getMessage(),
                ErrorCode.EVENT_EXPIRED.getCode(),
                detailsList,
                HttpStatus.GONE);
    }

    @ExceptionHandler(value = UnauthorizedUserException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccess(UnauthorizedUserException e) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("ErrorMessage: " + e.getMessage());
        detailsList.add("ErrorCause: " + e.getCause());
        return getErrorResponseEntity(
                ErrorCode.UNAUTHORIZED_ACCESS.getMessage(),
                ErrorCode.UNAUTHORIZED_ACCESS.getCode(),
                detailsList,
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        List<String> detailsList = new ArrayList<>();
        detailsList.add("ErrorMessage: " + e.getMessage());
        detailsList.add("ErrorCause: " + e.getCause());
        return getErrorResponseEntity(
                ErrorCode.NOT_FOUND.getMessage(),
                ErrorCode.NOT_FOUND.getCode(),
                detailsList,
                HttpStatus.NOT_FOUND);
    }
}
