package com.java.rigor.controllers;

import com.java.rigor.exception.ErrorView;
import com.java.rigor.exception.JavaRigorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by sanandasena on 2/5/2016.
 */
@ControllerAdvice
public class BaseController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public ResponseEntity<ErrorView> handleInternalServerError(JavaRigorException jre) {
        LOGGER.info("Entered handleInternalServerError(" + jre + ")");

        ErrorView errorView = new ErrorView(jre.getMessage(), jre.getErrorCode());
        return new ResponseEntity<>(errorView, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorView> handleUnAuthorizedError() {

        String errorMessage = "Access Denied!";
        ErrorView errorView = new ErrorView(errorMessage, null);
        return new ResponseEntity<>(errorView, HttpStatus.UNAUTHORIZED);
    }
}
