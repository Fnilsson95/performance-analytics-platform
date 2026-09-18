package com.performanceanalytics.workoutsession;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    // Optional<T> --> a container that holds zero or one value.
    // Use when the query implies at most one possible match, such as findByName where name is UNIQUE

    // List<T> --> a container that holds zero, one, or many values.
    // Use when the query allows multiple matches such as two sessions sharing a date.
    List<WorkoutSession> findBySessionDate(LocalDate sessionDate);
}
