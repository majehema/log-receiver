package com.example.logReceiver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicate that a field in the received data does not match
 * the Protocol Buffer message in <i>[file].proto</i>
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Data does not match the proto template")
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
