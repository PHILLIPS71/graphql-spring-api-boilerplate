package com.giantnodes.forum.utility.resources.conversion.image;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public enum ImageType {

    JPG(".jpg"),
    PNG(".png"),
    GIF(".gif"),
    SVG(".svg"),
    TIFF(".tif");

    private String mime;

    ImageType(String mime) {
        this.mime = mime;
    }

    public String getMime() {
        return mime;
    }

    public static Stream<ImageType> stream() {
        return Stream.of(ImageType.values());
    }

    public static ImageType toType(String mime) {
        System.out.println(mime);
        return ImageType.stream()
                .filter(type -> type.getMime().equalsIgnoreCase(mime) || type.name().equalsIgnoreCase(mime))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }


}
