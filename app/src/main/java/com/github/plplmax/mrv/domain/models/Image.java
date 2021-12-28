package com.github.plplmax.mrv.domain.models;

public class Image {
    private static final String IMAGE_UNAVAILABLE_NAME = "image_not_available";
    private String path;
    private String extension;

    public Image(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isAvailable() {
        return !path.contains(IMAGE_UNAVAILABLE_NAME);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return path + "." + extension;
    }
}
