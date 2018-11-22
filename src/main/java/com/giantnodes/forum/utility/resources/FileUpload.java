package com.giantnodes.forum.utility.resources;

import graphql.servlet.GraphQLContext;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileUpload {

    private final File file;
    private final GraphQLContext context;
    private ResourceLocation location;
    private String name;
    public FileUpload(GraphQLContext context, ResourceLocation location, String name) {
        this.context = context;
        this.location = location;
        this.name = name;
        this.file = store();
    }

    public File getFile() {
        return file;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public void setLocation(ResourceLocation location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File store() {
        if (context.getFiles().isPresent()) {
            List<Part> parts = context.getFiles().get().values()
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(part -> part.getContentType() != null)
                    .collect(Collectors.toList());

            for (Part part : parts) {
                try {
                    File file = new File(getLocation().getDirectory() + name + "." + part.getContentType().split("/")[1]);
                    FileUtils.copyInputStreamToFile(part.getInputStream(), file);
                    return file;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
