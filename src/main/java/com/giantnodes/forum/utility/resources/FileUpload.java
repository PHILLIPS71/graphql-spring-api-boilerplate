package com.giantnodes.forum.utility.resources;

import com.giantnodes.forum.utility.resources.conversion.image.ImageReformat;
import com.giantnodes.forum.utility.resources.conversion.image.ImageType;
import graphql.servlet.GraphQLContext;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileUpload {

    private final File file;
    private final GraphQLContext context;
    private ResourceDirectory location;
    private String name;

    public FileUpload(GraphQLContext context, ResourceDirectory location, String name) {
        this.context = context;
        this.location = location;
        this.name = name;
        this.file = store();
    }

    public File getFile() {
        return file;
    }

    public ResourceDirectory getLocation() {
        return location;
    }

    public void setLocation(ResourceDirectory location) {
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
                    return ImageReformat.convert(part.getInputStream(), ImageType.PNG, ResourceDirectory.STORAGE_AVATAR, name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
