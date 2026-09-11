CREATE TABLE workout_session (
    id BIGSERIAL PRIMARY KEY,
    session_date DATE NOT NULL,
    notes VARCHAR(1000),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

