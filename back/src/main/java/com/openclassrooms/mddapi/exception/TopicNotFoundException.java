package com.openclassrooms.mddapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TopicNotFoundException is a custom exception that represents a topic not found error.
 * It is thrown when a requested topic could not be found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Topic not found")
public class TopicNotFoundException extends Exception {
    /**
     * Constructs a new TopicNotFoundException with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public TopicNotFoundException(String message) {
        super(message);
    }
}
