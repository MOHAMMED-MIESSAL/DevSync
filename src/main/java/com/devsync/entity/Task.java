package com.devsync.entity;

import com.devsync.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name", nullable = false, length = 50)
    private String name;

    @Column(name = "task_description", nullable = false, length = 250)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private Status status;


    @Column(name = "dateEcheance", nullable = false)
    private LocalDateTime dateEcheance;

    @Column(name = "dateCreation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User userAffected;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User creator;

    // Relation Many-to-Many avec les tags
    @ManyToMany
    @JoinTable(
            name = "tasks_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();


    public Task(Long id, User creator, LocalDateTime dateEcheance, String description, String name, Status status, Set<Tag> tags, User userAffected) {
        this.id = id;
        this.creator = creator;
        this.dateEcheance = dateEcheance;
        this.dateCreation = LocalDateTime.now();
        this.description = description;
        this.name = name;
        this.status = status;
        this.tags = tags;
        this.userAffected = userAffected;
    }

    public Task() {
        this.dateCreation = LocalDateTime.now();
    }

    public Task(User creator, LocalDateTime dateEcheance, String description, String name, Status status, Set<Tag> tags, User userAffected) {
        this.creator = creator;
        this.dateEcheance = dateEcheance;
        this.dateCreation = LocalDateTime.now();
        this.description = description;
        this.name = name;
        this.status = status;
        this.tags = tags;
        this.userAffected = userAffected;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateEcheance=" + dateEcheance +
                ", dateCreation=" + dateCreation +
                ", userAffected=" + userAffected +
                ", creator=" + creator +
                ", tags=" + tags +
                '}';
    }
}
