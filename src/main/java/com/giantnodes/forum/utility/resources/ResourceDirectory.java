package com.giantnodes.forum.utility.resources;

public enum ResourceDirectory {

    STORAGE("storage/"),
    STORAGE_AVATAR("storage/avatar/");

    private String directory;

    ResourceDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return this.directory;
    }
}
