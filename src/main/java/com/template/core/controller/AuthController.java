package com.template.core.controller;

import com.template.core.model.Person;
import com.template.core.model.WebException;
import com.template.core.model.auth.AuthToken;
import com.template.core.model.auth.Credential;
import com.template.core.model.auth.DoubleAuthToken;
import com.template.core.repository.PersonsRepository;
import com.template.core.servise.AuthService;
import java.security.NoSuchAlgorithmException;
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
@RequestMapping(value={"/auth"})
public class AuthController {

    private final AuthService authService;

    @Autowired
    AuthController(AuthService authService) {
        this.authService =authService;
    }

    @RequestMapping(method={RequestMethod.GET}, value={"{vendor}"})
    public ResponseEntity<Person> authViaSocial(@PathVariable String vendor) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(method={RequestMethod.POST}, value={"/password"})
    public ResponseEntity<?> authViaCredential(@RequestBody Credential credential, HttpServletRequest request) {
        try {
            DoubleAuthToken token = authService.authViaCredential(request.getRemoteAddr(), credential);
            return new ResponseEntity(token, HttpStatus.OK);
        } catch (WebException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @RequestMapping(method={RequestMethod.POST}, value={"/token"})
    public ResponseEntity<?> generateAccessToken(@RequestBody AuthToken authToken, HttpServletRequest request) {
        AuthToken token = authService.generateAccessToken(authToken, request.getRemoteAddr());
        if (token != null) {
            return new ResponseEntity(token, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}