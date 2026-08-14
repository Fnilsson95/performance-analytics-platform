package com.performanceanalytics.exercise;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;


@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "muscle_group", nullable = false, length = 50)
    private String muscleGroup;

    @Column(nullable = false, length = 50)
    private String equipment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ExerciseType type;

    @Column(columnDefinition = "text")
    private String instructions;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    // Protected empty constructor
    protected Exercise() {
        // JPA only
    }

    // Constructor
    public Exercise(String name, String muscleGroup, String equipment, Difficulty difficulty,
                    ExerciseType type, String instructions) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.type = type;
        this.instructions = instructions;
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
    public String getName() { return name; }
    public String getMuscleGroup() { return muscleGroup; }
    public String getEquipment() { return equipment; }
    public Difficulty getDifficulty() { return difficulty; }
    public ExerciseType getType() { return type; }
    public String getInstructions() { return instructions; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Exercise other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
