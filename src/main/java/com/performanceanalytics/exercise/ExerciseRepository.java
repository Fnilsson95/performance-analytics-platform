package com.performanceanalytics.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

// Just an interface with no implementation needed. Spring Data JPA generates
// a working implementation of this at runtime, based on the two generic
// parameters: <Exercise, Long> means "this repository manages Exercise
// entities, whose primary key type is Long" (matching the type of
// Exercise.id).
//
// Extending JpaRepository gives you findAll(), findById(), save(), delete(),
// and pagination/sorting support.
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
