package com.devsync.service;

import com.devsync.entity.Tag;
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
        tagRepository.create(tag);
    }

    public void update(Tag tag) {
        tagRepository.update(tag);
    }

    public void delete(Long id) {
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
