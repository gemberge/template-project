package com.template.graphql;

import com.template.core.model.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value={"/graphql"})
public class GraphQLController {

    private final GraphQLServiceImpl graphQLService;

    @Autowired
    GraphQLController(GraphQLServiceImpl graphQLService) {
        this.graphQLService = graphQLService;
    }

    @RequestMapping(method={RequestMethod.POST})
    public ResponseEntity<?> executeQuery(@RequestBody String query) {
        try {
            Map<String, Object> result = graphQLService.query(query);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (WebException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }
}