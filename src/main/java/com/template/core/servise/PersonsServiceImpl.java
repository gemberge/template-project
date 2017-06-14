package com.template.core.servise;

import com.template.core.model.Person;
import com.template.core.model.WebException;
import com.template.core.repository.PersonsRepository;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PersonsServiceImpl {

    private static final int PAGE_SIZE = 50;
    @Inject
    private PersonsRepository personsRepository;

    public Person findById(long id) throws WebException {
        Person result = personsRepository.findOne(id);
        if(result != null) {
            return result;
        } else {
            throw new WebException(HttpStatus.NOT_FOUND, "");
        }
    }

    public List<Person> findAll() {
        return personsRepository.findAll();
    }

    public Person save(Person person) {
        person.setCreatedAt(new Date());
        person.setUpdatedAt(new Date());
        return personsRepository.save(person);
    }

    public Page<Person> getPersons(Integer pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, 50, Sort.Direction.DESC, new String[]{"username"});
        return this.personsRepository.findAll((Pageable)request);
    }
}