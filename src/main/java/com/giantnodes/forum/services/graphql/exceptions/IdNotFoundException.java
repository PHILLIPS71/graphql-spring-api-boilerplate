package com.giantnodes.forum.services.graphql.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class IdNotFoundException extends RuntimeException implements GraphQLError {


    private final String id;

    public IdNotFoundException(String id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Item with " + id + " could not be found!";
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }

}
