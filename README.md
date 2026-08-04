# Performance Analytics Platform

A backend system for tracking workouts, body measurements, and cardio sessions, with analytics and predictive insights layered on top. Built as a portfolio project to demonstrate production-grade Spring Boot engineering and not a tutorial CRUD app.

This project sits at the intersection of three things: a personal passion for working out and tracking progress, a current job that's physically demanding and worth monitoring the toll of, and a general interest in tracking day-to-day life through data. It's a system I actually want to use myself, which keeps the feature set grounded in real needs rather than arbitrary tutorial requirements.

Conceptually inspired by tools like Strava, but scoped specifically to showcase backend architecture, data modeling, and testing discipline.

---

## Table of Contents

- [Vision](#vision)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [How to Run Locally](#how-to-run-locally)
- [Running Tests](#running-tests)

---
test
## Vision

The long-term goal is a system that could realistically evolve into a SaaS product: a user tracks workouts, body metrics, and cardio sessions over time, and the platform surfaces trends, personal records, plateaus, and (eventually) predictions.

This project deliberately starts as a **modular monolith** rather than microservices with clear module boundaries, with the option to extract services later if there's ever a real reason to.

## Tech Stack

**Core**
- Java 21
- Spring Boot 4.1 / Spring Framework 7
- Spring Data JPA / Hibernate
- Spring Security 7 (JWT-based auth)
- PostgreSQL 16 (via Docker)
- Flyway (versioned schema migrations)
- Maven

**Testing**
- JUnit 5
- Mockito
- Spring Boot Test
- Testcontainers (integration tests against a real Postgres instance)

**Planned**
- Redis (caching)
- A separate analytics/ML service (likely Python) once enough historical data models are in place
- Cloud deployment (TBD)

## Architecture

**TBA**

## How to Run Locally

```bash
# 1. Start PostgreSQL (runs on localhost:5433)
docker compose up -d

# 2. Confirm the container is up
docker ps

# 3. Run the application (Flyway migrations run automatically on startup)
./mvnw spring-boot:run
```

App runs on `http://localhost:8080`.

Quick health check:
```bash
curl http://localhost:8080/actuator/health
```

## Running Tests

```bash
# Unit tests only
./mvnw test

# Full test suite, including Testcontainers-backed integration tests
./mvnw verify
```

Integration tests spin up a real PostgreSQL container (pinned to match the production image version) rather than mocking the database. This catches real SQL/constraint issues that an in-memory or mocked datasource would miss.