package com.devsync.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    // Many-to-Many relation with taches
    @ManyToMany(mappedBy = "tags")
    private Set<Task> taches = new HashSet<>();

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        setName(name);
    }

    public Tag(String name) {
        setName(name);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du tag ne peut pas être vide.");
        }
        this.name = name.trim();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}