package org.movie.portfolioapidevelopment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public enum SkillLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
    }

    // Constructors
    public Skill() {}

    public Skill(String name, SkillLevel level, String category, User user) {
        this.name = name;
        this.level = level;
        this.category = category;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public SkillLevel getLevel() { return level; }
    public void setLevel(SkillLevel level) { this.level = level; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}