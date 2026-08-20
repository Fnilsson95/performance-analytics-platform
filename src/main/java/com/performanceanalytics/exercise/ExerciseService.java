package com.performanceanalytics.exercise;

import org.springframework.stereotype.Service;

// @Service marks this as a Spring-managed bean, same mechanism as @RestController for controllers
// Spring creates and injects it automatically wherever it's needed.
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    // Contructor
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    // Create a new exercise
    public Exercise createExercise(ExerciseCreateRequest request) {

        // Validate that no exercise with same name already exists.
        // We validate here and at database creation with UNIQUE constraint.
        if (exerciseRepository.findByName(request.name()).isPresent()) {
            throw new DuplicateExerciseException(request.name());
        }

        // New exercise
        Exercise exercise = new Exercise(
                request.name(),
                request.muscleGroup(),
                request.equipment(),
                request.difficulty(),
                request.type(),
                request.instructions()
        );

        // save() inserts the row. Hibernate/Postgres assign the id via
        // the BIGSERIAL sequence, and @PrePersist stamps createdAt/
        // updatedAt, the returned Exercise now has all three populated,
        // even though the object created above don't have them yet.
        return exerciseRepository.save(exercise);
    }



} // End of class
