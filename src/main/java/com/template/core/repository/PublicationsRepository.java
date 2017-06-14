package com.template.core.repository;

import com.template.core.model.Person;
import com.template.core.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationsRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByAuthor(Person author);
}