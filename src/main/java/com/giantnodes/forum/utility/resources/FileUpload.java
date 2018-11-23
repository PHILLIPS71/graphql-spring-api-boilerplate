package com.giantnodes.forum.utility.resources;

import graphql.servlet.GraphQLContext;

import javax.servlet.http.Part;
import java.io.File;
import java.util.Collection;

public abstract class FileUpload {

    private final GraphQLContext context;
    private File file;
    private ResourceDirectory resource;
    private String name;

    public FileUpload(GraphQLContext context, ResourceDirectory resource, String name) {
        this.context = context;
        this.resource = resource;
        this.name = name;
    }

    public GraphQLContext getContext() {
        return context;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ResourceDirectory getResource() {
        return resource;
    }

    public void setResource(ResourceDirectory resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Part getPart() {
        return context.getFiles().get().values()
                .stream()
                .flatMap(Collection::stream)
                .filter(part -> part.getContentType() != null)
                .findAny().orElseThrow(null);
    }

    public abstract void store();

}
