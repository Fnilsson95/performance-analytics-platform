package com.performanceanalytics.exercise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// The INBOUND REQUEST DTO: the shape a client sends on POST to
// create a new exercise. Smaller than ExerciseResponse: no id,
// no createdAt/updatedAt, because none of those are known yet at creation
// time, and they are all generated server-side (id by the database sequence,
// timestamps by Exercise's @PrePersist callback).
//
// No static factory method here, unlike ExerciseResponse. That DTO needed
// entity -> DTO conversion (from()). This DTO goes the other direction.
// Spring/Jackson builds it directly from incoming JSON, using the record's
// constructor. The reverse conversion (request -> entity) belongs
// in ExerciseService, not here, since deciding how to construct an Exercise
// is service-layer responsibility.
public record ExerciseCreateRequest(

        // @NotBlank rejects null, "", and whitespace-only strings.
        // Stronger than @NotNull, which would let "" through.
        @NotBlank
        String name,

        @NotBlank
        String muscleGroup,

        @NotBlank
        String equipment,

        // @NotNull, not @NotBlank — Difficulty is an enum, not a String,
        // so @NotBlank (which only works on CharSequence types) won't
        // compile here.
        @NotNull
        Difficulty difficulty,

        @NotNull
        ExerciseType type,

        // Optional: no constraints needed.
        String instructions
) { // Parameters above


}
