package com.github.plplmax.mrv.domain.models;

import java.util.Objects;

public class Image {
    private static final String IMAGE_VARIANT = "portrait_xlarge";

    private final String path;
    private final String extension;

    public Image(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return path + "/" + IMAGE_VARIANT + "." + extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(path, image.path) && Objects.equals(extension, image.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, extension);
    }
}
