package com.ghostnet.repository;

import com.ghostnet.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    List<Person> findByIsAnonymous(boolean isAnonymous);
    
    List<Person> findByNameContainingIgnoreCase(String name);
}