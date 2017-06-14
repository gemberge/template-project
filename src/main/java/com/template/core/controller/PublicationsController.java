package com.template.core.controller;

import com.template.core.model.Publication;
import com.template.core.model.WebException;
import com.template.core.servise.AuthService;
import com.template.core.servise.PublicationsServiceImpl;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/publications"})
public class PublicationsController {

    private final PublicationsServiceImpl publicationsService;
    private final AuthService authService;

    @Autowired
    PublicationsController(PublicationsServiceImpl publicationsService, AuthService authService) {
        this.publicationsService = publicationsService;
        this.authService = authService;
    }

    @RequestMapping(method={RequestMethod.GET})
    public ResponseEntity<Collection<Publication>> getAll() {
        return new ResponseEntity(publicationsService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method={RequestMethod.GET}, value={"{id}"})
    public ResponseEntity<Publication> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity(publicationsService.findById(id), HttpStatus.OK);
        } catch (WebException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @RequestMapping(method={RequestMethod.POST})
    public ResponseEntity<?> create(@RequestBody Publication publication, HttpServletRequest request) {
        try {
            long currentUserId = authService.authViaHeader(request);
            publication = publicationsService.post(publication, currentUserId);
            return new ResponseEntity(publication, HttpStatus.CREATED);
        }
        catch (WebException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @RequestMapping(method={RequestMethod.DELETE}, value={"{id}"})
    public ResponseEntity<?> delete(@PathVariable String id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(method={RequestMethod.PUT}, value={"{id}"})
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Publication publication) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}