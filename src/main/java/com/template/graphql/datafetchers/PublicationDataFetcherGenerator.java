package com.template.graphql.datafetchers;

import com.template.core.model.Person;
import com.template.core.model.WebException;
import com.template.core.servise.PublicationsServiceImpl;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class PublicationDataFetcherGenerator {

    private final PublicationsServiceImpl publicationsService;

    @Autowired
    PublicationDataFetcherGenerator(PublicationsServiceImpl publicationsService) {
        this.publicationsService = publicationsService;
    }

    public DataFetcher getPersonPublicationsDataFetcher() {
        return environment -> {
            try {
                return publicationsService.findByAuthor((Person) environment.getSource());
            } catch (WebException e) {
                e.printStackTrace();
                return new LinkedList<>();
            }
        };
    }

    public DataFetcher getPublicationsDataFetcher() {
        return environment -> publicationsService.findAll();
    }
}
