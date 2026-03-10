package com.customer.rewards.exception;

import java.time.Instant;

/**
 * Standard API error response.
 */
public class ApiErrorResponse {
    private Instant timestamp = Instant.now();
    private int status;
    private String message;

    public ApiErrorResponse() { }

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
