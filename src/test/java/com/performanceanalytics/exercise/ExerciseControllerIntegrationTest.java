package com.performanceanalytics.exercise;

/*
 * Integration test for the exercise API, Postgres via Testcontainers.
 *
 * @Import(TestcontainersConfiguration.class) starts the container;
 * @SpringBootTest(RANDOM_PORT) boots the app and runs Flyway against it;
 * @AutoConfigureTestRestTemplate is required in Spring Boot 4 to inject
 * TestRestTemplate, which each test uses to call /api/v1/exercises.
 *
 * Covers: GET empty list, POST success (201), POST duplicate name (409).
 */

import com.performanceanalytics.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import com.performanceanalytics.exception.ErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ExerciseControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllExercises_returnsEmptyList_whenNoExercisesExist() {
        ResponseEntity<ExerciseResponse[]> response =
                restTemplate.getForEntity("/api/v1/exercises", ExerciseResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }


    @Test
    void createExercise_returnsCreated_withValidRequest() {
        ExerciseCreateRequest request = new ExerciseCreateRequest(
                "Squat", "Legs", "Barbell", Difficulty.INTERMEDIATE, ExerciseType.RESISTANCE, "Bar on back, squat down."
        );

        ResponseEntity<ExerciseResponse> response =
                restTemplate.postForEntity("/api/v1/exercises", request, ExerciseResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Squat");
        assertThat(response.getBody().id()).isNotNull();
    }

    @Test
    void createExercise_returnsConflict_whenNameAlreadyExists() {
        ExerciseCreateRequest request = new ExerciseCreateRequest(
                "Deadlift", "Back", "Barbell", Difficulty.ADVANCED, ExerciseType.RESISTANCE, "Lift bar from floor."
        );

        ResponseEntity<ExerciseResponse> firstResponse =
                restTemplate.postForEntity("/api/v1/exercises", request, ExerciseResponse.class);
        assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<ErrorResponse> secondResponse =
                restTemplate.postForEntity("/api/v1/exercises", request, ErrorResponse.class);

        assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(secondResponse.getBody()).isNotNull();
        assertThat(secondResponse.getBody().message()).contains("Deadlift");
    }
}