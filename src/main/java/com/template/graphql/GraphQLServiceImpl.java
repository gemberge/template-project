package com.template.graphql;

import com.template.core.model.WebException;
import com.template.graphql.datafetchers.PersonDataFetcherGenerator;
import com.template.graphql.datafetchers.PublicationDataFetcherGenerator;
import com.template.graphql.presenters.CoverGraphQLPresenter;
import com.template.graphql.presenters.PersonGraphQLPresenter;
import com.template.graphql.presenters.PublicationGraphQLPresenter;
import graphql.GraphQL;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static graphql.Scalars.GraphQLLong;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

@Service
@Transactional
public class GraphQLServiceImpl {

    private GraphQLSchema schema;
    private final PersonDataFetcherGenerator personDataFetcherGenerator;
    private final PublicationDataFetcherGenerator publicationDataFetcherGenerator;

    @Autowired
    GraphQLServiceImpl(PersonDataFetcherGenerator personDataFetcherGenerator, PublicationDataFetcherGenerator publicationDataFetcherGenerator) {
        this.personDataFetcherGenerator = personDataFetcherGenerator;
        this.publicationDataFetcherGenerator = publicationDataFetcherGenerator;
    }

    public Map<String, Object> query(String query) throws WebException {
        return (Map<String, Object>) new GraphQL(getSchema()).execute(query).getData();
    }

    private GraphQLSchema getSchema() {
        if(schema == null) {
            schema = createSchema();
        }
        return schema;
    }

    private GraphQLSchema createSchema() {
        GraphQLObjectType publicationType = PublicationGraphQLPresenter.generateGraphQLObjectType();
        GraphQLObjectType personType = PersonGraphQLPresenter.generateGraphQLObjectType(publicationDataFetcherGenerator);
        GraphQLObjectType coverType = CoverGraphQLPresenter.generateGraphQLObjectType();

        GraphQLObjectType queryType = newObject()
                .name("QueryType")
                .field(newFieldDefinition()
                        .type(personType)
                        .name("person")
                        .argument(newArgument()
                                .name("id")
                                .type(new GraphQLNonNull(GraphQLLong))
                                .build())
                        .dataFetcher(personDataFetcherGenerator.getPersonDataFetcher()))
                .field(newFieldDefinition()
                        .type(new GraphQLList(personType))
                        .name("persons")
                        .argument(newArgument()
                                .name("id")
                                .type(GraphQLLong)
                                .build())
                        .dataFetcher(personDataFetcherGenerator.getPersonsDataFetcher()))
                .field(newFieldDefinition()
                        .type(new GraphQLList(publicationType))
                        .name("publications")
                        .dataFetcher(publicationDataFetcherGenerator.getPublicationsDataFetcher()))
                .field(newFieldDefinition()
                        .type(coverType)
                        .name("cover"))
                .build();

        return GraphQLSchema.newSchema().query(queryType).build();
    }
}