package com.example.repeating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Reposiotry extends JpaRepository<Entity, Integer> {
}
