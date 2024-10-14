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
@Table(name = "taches")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "utilisateur_affecte_id", nullable = false)
    private User utilisateurAffecte;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    // Many-to-Many relation with tags
    @ManyToMany
    @JoinTable(
            name = "tache_tags",
            joinColumns = @JoinColumn(name = "tache_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Task(Long id, User creator, LocalDateTime dateCreation, String name, Status status, User utilisateurAffecte) {
        this.creator = creator;
        this.dateCreation = dateCreation;
        this.id = id;
        this.name = name;
        this.status = status;
        this.utilisateurAffecte = utilisateurAffecte;
    }

    public Task(Long id, Optional<User> creator, LocalDateTime dateCreation, String name, Status status, Optional<User> utilisateurAffecte) {
        this.creator = creator.get();
        this.dateCreation = dateCreation;
        this.id = id;
        this.name = name;
        this.status = status;
        this.utilisateurAffecte = utilisateurAffecte.get();
    }

    public Task() {
    }
}
