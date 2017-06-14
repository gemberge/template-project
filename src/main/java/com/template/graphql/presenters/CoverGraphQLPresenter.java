package com.template.graphql.presenters;

import graphql.schema.GraphQLObjectType;

import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class CoverGraphQLPresenter {

    public static GraphQLObjectType generateGraphQLObjectType() {
        return newObject()
                .name("Cover")
                .field(newFieldDefinition()
                        .type(GraphQLLong)
                        .name("id"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("quote"))
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("imageUrl"))
                .build();
    }

}
