package com.example.repeating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredenRepository extends JpaRepository<Creden, Integer> {
    public Creden findByMessageId(String messageId);
}