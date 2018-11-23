package com.giantnodes.forum.utility.resources;

public enum ResourceLocation {

    STORAGE("storage/"),
    STORAGE_AVATAR("storage/avatar/");

    private String directory;

    ResourceLocation(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return this.directory;
    }
}
