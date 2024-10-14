package com.devsync.repository.interfaces;

import com.devsync.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    void create(Tag tag);
    void update(Tag tag);
    void delete(Long id);
    Optional<Tag> findById(Long id);
    List<Tag> findAll();
    Optional<Tag> findByName(String name);
}
