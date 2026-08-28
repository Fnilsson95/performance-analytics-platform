package com.performanceanalytics.exception;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String message,
        Instant timestamp
) {

    // Only need to supply status and message, timestamp will always be "now".
    public ErrorResponse (int status, String message) {
        this(status, message, Instant.now());
    }

} // End of record
