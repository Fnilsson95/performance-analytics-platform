-- session_exercise - join table linking a workout_Session to the exercises performed in it,
-- in a specific order (position). One row = exercise X was the Nth exercise in the session Y
CREATE TABLE session_exercise (


    id BIGSERIAL PRIMARY KEY,

    -- a session_exercise has no meaning without its session.
    -- Deleting the session deletes all its session_exercise rows automatically.
    workout_session_id BIGINT NOT NULL REFERENCES workout_session(id) ON DELETE CASCADE,

    -- exercise is shared data, not owned by any one session.
    -- RESTRICT blocks deleting an exercise that's still used in historical sessions.
    exercise_id BIGINT NOT NULL REFERENCES exercise(id) ON DELETE RESTRICT,

    -- Order of this exercise within its session. 1-indexed by convention.
    -- Gaps are allowed, such as after a deletion
    position INTEGER NOT NULL CHECK (position >= 1),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    -- No two exercises in the same session can share a position.
    -- Also the safety net for the MAX(position)+1 race condition in the service layer.
    CONSTRAINT uq_session_exercise_position UNIQUE (workout_session_id, position)
);

-- Postgres does not auto-index FK columns
CREATE INDEX idx_session_exercise_workout_session_id ON session_exercise(workout_session_id);
CREATE INDEX idx_session_exercise_exercise_id ON session_exercise(exercise_id);