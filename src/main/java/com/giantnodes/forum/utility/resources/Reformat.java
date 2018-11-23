package com.giantnodes.forum.utility.resources;

import java.io.File;

public abstract class Reformat<T> {

   private final File file;
   private T format;

    public Reformat(File file, T format) {
        this.file = file;
        this.format = format;
    }

    public File getFile() {
        return file;
    }

    public T getFormat() {
        return format;
    }

    public void setFormat(T format) {
        this.format = format;
    }

    public abstract File convert();
}
