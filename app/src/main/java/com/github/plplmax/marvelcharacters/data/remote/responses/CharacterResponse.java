package com.github.plplmax.marvelcharacters.data.remote.responses;

public class CharacterResponse {
    public int id;
    public String name;
    public String description;
    public ImageResponse thumbnail;

    public CharacterResponse(int id, String name, String description, ImageResponse thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }
}