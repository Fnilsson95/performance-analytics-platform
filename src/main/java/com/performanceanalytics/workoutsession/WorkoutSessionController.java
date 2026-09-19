package com.performanceanalytics.workoutsession;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workout-sessions")
public class WorkoutSessionController {


    private final WorkoutSessionRepository workoutSessionRepository;


    public WorkoutSessionController(WorkoutSessionRepository workoutSessionRepository) {
        this.workoutSessionRepository = workoutSessionRepository;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutSessionResponse>> getAllWorkoutSessions() {
        List<WorkoutSessionResponse> workoutSessions = workoutSessionRepository.findAll()
                .stream()
                .map(WorkoutSessionResponse::from)
                .toList();

        return ResponseEntity.ok(workoutSessions);
    }

    @PostMapping
    public ResponseEntity<WorkoutSessionResponse> createWorkoutSession(@Valid @RequestBody WorkoutSessionCreateRequest request) {
        WorkoutSession workoutSession = new WorkoutSession(request.sessionDate(), request.notes());

        WorkoutSession saved = workoutSessionRepository.save(workoutSession);

        return ResponseEntity.status(HttpStatus.CREATED).body(WorkoutSessionResponse.from(saved));
    }

}
