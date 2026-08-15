package com.performanceanalytics.exercise;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController = @Controller + @ResponseBody. Every method's return
// value is serialized straight to the HTTP response body (as JSON, via
// Jackson) instead of being resolved as a view name.
@RestController
// Base path for every endpoint in this class. Individual @GetMapping /
// @PostMapping methods only need to add whatever comes after this.
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    // Constructor injection, final field, no @Autowired needed, there's
    // only one constructor, so Spring uses it automatically.
    //
    // Talking to the repository directly here, with no service layer in
    // between: this endpoint has zero business logic, it's a pure
    // "list everything" read. A service here would just be an empty
    // pass-through. That changes the moment there's an actual rule to
    // enforce (I.e. validating uniqueness before a POST) and at that point
    // an ExerciseService gets introduced
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    // @GetMapping with no path = handles GET requests to exactly the
    // class-level base path, "/api/v1/exercises"
    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getAllExercises() {
        List<ExerciseResponse> exercises = exerciseRepository.findAll()
                .stream()
                .map(ExerciseResponse::from)
                .toList();

        // ResponseEntity.ok() explicitly returns 200 OK with the body.
        // Using ResponseEntity instead of returning List<ExerciseResponse>
        // directly gives explicit control over the status code and not
        // strictly necessary for a simple GET (Spring defaults to 200
        // anyway), but it's the right habit: you need this control the
        // moment you build endpoints that return 201 Created, 404 Not
        // Found, etc.
        return ResponseEntity.ok(exercises);
    }


}
