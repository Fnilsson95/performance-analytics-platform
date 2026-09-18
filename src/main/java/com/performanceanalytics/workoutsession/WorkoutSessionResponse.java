package com.performanceanalytics.workoutsession;

import java.time.Instant;
import java.time.LocalDate;

public record WorkoutSessionResponse(
        Long id,
        LocalDate sessionDate,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {

    public static WorkoutSessionResponse from(WorkoutSession workoutSession) {
        return new WorkoutSessionResponse(
                workoutSession.getId(),
                workoutSession.getSessionDate(),
                workoutSession.getNotes(),
                workoutSession.getCreatedAt(),
                workoutSession.getUpdatedAt()
        );
    }
} // End of WorkoutSession record
