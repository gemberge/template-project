package com.template.core.repository;

import com.template.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Person, Long> {
    public Person findByUsername(String var1);
}