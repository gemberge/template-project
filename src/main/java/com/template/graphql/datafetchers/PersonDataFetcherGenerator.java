package com.template.graphql.datafetchers;

import com.template.core.model.Person;
import com.template.core.model.WebException;
import com.template.core.servise.PersonsServiceImpl;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class PersonDataFetcherGenerator {

    private final PersonsServiceImpl personsService;

    @Autowired
    PersonDataFetcherGenerator(PersonsServiceImpl personsService) {
        this.personsService = personsService;
    }

    public DataFetcher getPersonDataFetcher() {
        return environment -> {
            try {
                return personsService.findById(environment.getArgument("id"));
            } catch (WebException e) {
                return null;
            }
        };
    }

    public DataFetcher getPersonsDataFetcher() {
        return environment -> {
            Long id = environment.getArgument("id");
            if(id != null) {
                List result = new LinkedList();
                Person person = null;
                try {
                    person = personsService.findById(id);
                } catch (WebException e) {
                    e.printStackTrace();
                }
                if(person != null) result.add(person);
                return result;
            } else {
                return personsService.findAll();
            }
        };
    }
}
