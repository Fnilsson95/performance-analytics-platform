package com.performanceanalytics.exercise;

// Extends RuntimeException (unchecked), not Exception (checked).
// Unchecked means callers aren't forced to declare "throws
// DuplicateExerciseException" or wrap every call in try/catch. This
// exception represents a business rule violation, not a recoverable,
// expected-in-normal-flow condition (like a file not being found),
// it should propagate naturally up to wherever it's actually handled
// (later: a global exception handler translating it to HTTP 409),
// without every intermediate method needing to know about it.
public class DuplicateExerciseException  extends RuntimeException {
    public DuplicateExerciseException(String name) {
        super("Exercise already exists with name: " + name);
    }
}
