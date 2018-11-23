package com.giantnodes.forum.utility.resources.conversion.image;

import com.giantnodes.forum.utility.resources.ResourceDirectory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageReformat {

    public static File convert(InputStream stream, ImageType type, ResourceDirectory resource, String name) {
        File file = new File(resource.getDirectory() + name + type.getMime());
        try {
            ImageIO.write(ImageIO.read(stream), type.name().toLowerCase(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
