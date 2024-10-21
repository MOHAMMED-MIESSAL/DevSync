package com.devsync.service;

import com.devsync.entity.Tag;
import com.devsync.exceptions.ResourceNotFoundException;
import com.devsync.exceptions.ValidationException;
import com.devsync.repository.implementations.TagRepositoryImpl;
import com.devsync.repository.interfaces.TagRepository;

import java.util.List;
import java.util.Optional;

public class TagService {
    private final TagRepository tagRepository;

    public TagService() {

        this.tagRepository = new TagRepositoryImpl();
    }

    public void create(Tag tag) {
        if (tag.getName() == null || tag.getName().isEmpty() || tag.getName().isBlank()) {
            throw new ValidationException("Le nom du tag ne peut pas être vide.");
        }

        tagRepository.create(tag);
    }

    public void update(Tag tag) {

        if (tag.getId() == null) {
            throw new ValidationException("L'ID du tag ne peut pas être nul.");
        }

        Optional<Tag> existingTag = tagRepository.findById(tag.getId());
        if (existingTag.isEmpty()) {
            throw new ResourceNotFoundException("Le tag avec l'ID " + tag.getId() + " n'existe pas.");
        }

        // Valider le nom du tag
        if (tag.getName() == null || tag.getName().isEmpty() || tag.getName().isBlank()) {
            throw new ValidationException("Le nom du tag ne peut pas être vide.");
        }

        // Si toutes les validations sont valides, mettre à jour le tag
        tagRepository.update(tag);
    }

    public void delete(Long id) {

        if (id == null) {
            throw new ValidationException("L'ID du tag ne peut pas être nul.");
        }

        Optional<Tag> existingTag = tagRepository.findById(id);

        if (existingTag.isEmpty()) {
            throw new ResourceNotFoundException("Le tag avec l'ID " + id + " n'existe pas.");
        }

        tagRepository.delete(id);
    }


    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
    public  Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

}
