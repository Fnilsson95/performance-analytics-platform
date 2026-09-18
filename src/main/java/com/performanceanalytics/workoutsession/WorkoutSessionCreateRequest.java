package com.performanceanalytics.workoutsession;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record WorkoutSessionCreateRequest(

        @NotNull
        LocalDate sessionDate,

        // Optional: no constraint needed
        String notes
) {
}