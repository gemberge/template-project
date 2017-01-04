package com.template.core.controller;

import com.template.core.model.Person;
import com.template.core.model.WebException;
import com.template.core.servise.AuthService;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import com.template.core.servise.PersonsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/persons"})
public class PersonsController {

    private final PersonsServiceImpl personsService;
    private final AuthService authService;

    @Autowired
    PersonsController(PersonsServiceImpl personsService, AuthService authService) {
        this.personsService = personsService;
        this.authService = authService;
    }

    @RequestMapping(method={RequestMethod.GET})
    public ResponseEntity<Collection<Person>> getAll() {
        return new ResponseEntity(personsService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method={RequestMethod.GET}, value={"/current"})
    public ResponseEntity<Person> getCurrent(HttpServletRequest request) {
        try {
            long id = authService.authViaHeader(request);
            return new ResponseEntity(personsService.findById(id), HttpStatus.OK);
        } catch (WebException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @RequestMapping(method={RequestMethod.GET}, value={"{id}"})
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity(personsService.findById(id), HttpStatus.OK);
        } catch (WebException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

//    @RequestMapping(method={RequestMethod.GET}, params={"username"})
//    public ResponseEntity<Person> findByUsername(@RequestParam(value="username") String username) {
//        Person person = this.repository.findByUsername(username);
//        if (person != null) {
//            return new ResponseEntity((Object)person, HttpStatus.OK);
//        }
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }

    @RequestMapping(method={RequestMethod.POST})
    public ResponseEntity<?> create(@RequestBody Person person) {
        return new ResponseEntity(personsService.save(person), HttpStatus.CREATED);
    }

    @RequestMapping(method={RequestMethod.DELETE}, value={"{id}"})
    public ResponseEntity<?> delete(@PathVariable String id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(method={RequestMethod.PUT}, value={"{id}"})
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Person person) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}