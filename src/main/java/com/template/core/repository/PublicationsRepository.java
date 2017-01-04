package com.template.core.repository;

import com.template.core.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationsRepository extends JpaRepository<Publication, Long> {
}