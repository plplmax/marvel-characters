package com.github.plplmax.marvelcharacters.data.remote.responses;

public class ImageResponse {
    public String path;
    public String extension;

    public ImageResponse(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }
}