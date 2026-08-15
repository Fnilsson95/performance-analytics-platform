package com.performanceanalytics.exercise;


import java.time.Instant;

// A DTO (Data Transfer Object): the shape the API actually exposes to
// clients, independent of how the database/entity is structured. This is
// what the controller returns and never the Exercise entity itself.
//
// A "record" is used here instead of a class: Java generates the
// constructor, accessors, equals/hashCode, and toString automatically from
// this one line. DTOs are immutable, simple carriers of data and none of the
// JPA identity/lazy-loading concerns that made equals/hashCode tricky on
// Exercise apply here, so a record is a clean, boilerplate-free fit.
public record ExerciseResponse(
        Long id,
        String name,
        String muscleGroup,
        String equipment,
        Difficulty difficulty,
        ExerciseType type,
        String instructions,
        Instant createdAt,
        Instant updatedAt
) { // Parameters above

    // Static factory method: converts an Exercise entity into its API-facing
    // DTO. Keeping this mapping logic here, colocated with the type it
    // produces means the controller doesn't need to know the details of
    // how an Exercise becomes an ExerciseResponse, it just calls
    // ExerciseResponse.from(exercise)
    public static ExerciseResponse from(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getMuscleGroup(),
                exercise.getEquipment(),
                exercise.getDifficulty(),
                exercise.getType(),
                exercise.getInstructions(),
                exercise.getCreatedAt(),
                exercise.getUpdatedAt()
        );
    }

} // End of ExerciseResponse record/class
