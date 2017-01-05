package com.template.graphql.presenters;

import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;

import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class PublicationGraphQLPresenter {

    public static GraphQLObjectType generateGraphQLObjectType() {
        return newObject()
                .name("Publication")
                .field(newFieldDefinition()
                        .type(GraphQLLong)
                        .name("id"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("title"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("description"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("content"))
                .field(newFieldDefinition()
                        .type(new GraphQLTypeReference("Cover"))
                        .name("cover"))
                .field(newFieldDefinition()
                        .type(new GraphQLTypeReference("Person"))
                        .name("author"))
                .build();
    }

}
