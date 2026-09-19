package com.performanceanalytics.workoutsession;


import com.performanceanalytics.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class WorkoutSessionControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WorkoutSessionRepository workoutSessionRepository;

    @BeforeEach
    void cleanUp() {
        workoutSessionRepository.deleteAll();
    }

    @Test
    void getAllWorkoutSessions_returnEmptyList_whenNoneExist() {
        ResponseEntity<WorkoutSessionResponse[]> response =
                restTemplate.getForEntity("/api/v1/workout-sessions", WorkoutSessionResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void createWorkoutSession_returnsCreated_withValidRequest() {
        WorkoutSessionCreateRequest request = new WorkoutSessionCreateRequest(
                LocalDate.of(2026, 9, 15), "Leg Day"
        );

        ResponseEntity<WorkoutSessionResponse> response =
                restTemplate.postForEntity("/api/v1/workout-sessions", request, WorkoutSessionResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sessionDate()).isEqualTo(LocalDate.of(2026, 9, 15));
        assertThat(response.getBody().id()).isNotNull();
    }

    @Test
    void createWorkoutSession_returnsBadRequest_whenSessionDateIsNull() {

        WorkoutSessionCreateRequest request = new WorkoutSessionCreateRequest(null, "some random notes");

        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/v1/workout-sessions", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }
}
