package com.template.core.servise;

import com.template.core.model.Cover;
import com.template.core.model.Person;
import com.template.core.model.Publication;
import com.template.core.model.WebException;
import com.template.core.repository.PersonsRepository;
import com.template.core.repository.PublicationsRepository;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublicationsServiceImpl {

    private final ValidationServiceImpl validator;
    private final PublicationsRepository publicationsRepository;
    private final PersonsRepository personsRepository;

    @Inject
    PublicationsServiceImpl(ValidationServiceImpl validationService, PublicationsRepository publicationsRepository, PersonsRepository personsRepository) {
        this.validator = validationService;
        this.publicationsRepository = publicationsRepository;
        this.personsRepository = personsRepository;
    }

    public List<Publication> findAll() {
        return publicationsRepository.findAll();
    }

    public Publication findById(Long id) throws WebException {
        Publication result = publicationsRepository.findOne(id);
        validator.notNull(result, HttpStatus.NOT_FOUND, "There isn't publication with such id");
        return result;
    }

    public Publication post(Publication publication, Long authorId) throws WebException {
        Person author = personsRepository.findOne(authorId);
        validator.notNull(author, HttpStatus.UNAUTHORIZED, "Can't find person with such id");
        validator.notNull(publication, HttpStatus.BAD_REQUEST, "Publication can't be null");
        validator.notNullAndNotEmpty(publication.getContent(), HttpStatus.BAD_REQUEST, "Content should be specified");

        if (publication.getDescription() == null) publication.setDescription("");
        publication.setAuthor(author);
        return publicationsRepository.save(publication);
    }
}