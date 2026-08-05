CREATE TABLE exercise (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    muscle_group VARCHAR(50) NOT NULL,
    equipment VARCHAR(50) NOT NULL,
    difficulty VARCHAR(20) NOT NULL
                      CHECK (difficulty IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')),
    type VARCHAR(20) NOT NULL
                      CHECK (type IN ('RESISTANCE', 'CARDIO')),
    instructions TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);