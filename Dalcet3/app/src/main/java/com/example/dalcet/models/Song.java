package com.example.dalcet.models;

public class Song {
    private String name;
    private int resourceId;

    public Song(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }
}
