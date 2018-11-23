package com.giantnodes.forum.utility.resources.conversion.image;

import com.giantnodes.forum.utility.resources.ResourceDirectory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    public static File convert(InputStream stream, ImageType type, ResourceDirectory resource, String name, int width, int height) {
        File file = new File(resource.getDirectory() + name + type.getMime());
        try {
            BufferedImage buffer = ImageIO.read(stream);
            ImageIO.write(resize(buffer, width, height), type.name().toLowerCase(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        if(image.getWidth() <= width || image.getHeight() <= height) {
            return image;
        }

        BufferedImage resized = new BufferedImage(width, height, image.getType());
        Graphics2D graphics = resized.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        return resized;
    }
}
