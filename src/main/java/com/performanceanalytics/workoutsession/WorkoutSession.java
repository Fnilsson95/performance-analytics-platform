package com.performanceanalytics.workoutsession;


import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "workout_session") // Explicitly naming table to remove ambiguity and "self-document"
public class WorkoutSession {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(length = 1000)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    protected WorkoutSession() {

    }

    public WorkoutSession(LocalDate sessionDate, String notes) {
        this.sessionDate = sessionDate;
        this.notes = notes;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }


    // Getters


    public Long getId() {
        return id;
    }
    public LocalDate getSessionDate() {
        return sessionDate;
    }
    public String getNotes() { return notes;}
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof WorkoutSession other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }

} // End of class