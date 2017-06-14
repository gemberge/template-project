package com.template.graphql.presenters;

import com.template.graphql.datafetchers.PublicationDataFetcherGenerator;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;

import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class PersonGraphQLPresenter {

    public static GraphQLObjectType generateGraphQLObjectType(PublicationDataFetcherGenerator publicationDataFetcherGenerator) {
        return newObject()
                .name("Person")
                .field(newFieldDefinition()
                        .type(GraphQLLong)
                        .name("id"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("firstName"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("lastName"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("username"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("email"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("description"))
                .field(newFieldDefinition()
                        .name("publications")
                        .type(new GraphQLList(new GraphQLTypeReference("Publication")))
                        .dataFetcher(publicationDataFetcherGenerator.getPersonPublicationsDataFetcher()))
                .build();
    }

}
