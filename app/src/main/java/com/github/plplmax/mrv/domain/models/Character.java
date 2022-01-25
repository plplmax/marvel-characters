package com.github.plplmax.mrv.domain.models;

import java.io.Serializable;
import java.util.Objects;

public class Character implements Serializable {
    private int id;
    private String name;
    private String description;
    private Image thumbnail;

    public Character(int id, String name, String description, Image thumbnail) {
        setId(id);
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.thumbnail = Objects.requireNonNull(thumbnail);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 1) throw new IllegalArgumentException("id must be greater than 0");

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id &&
                Objects.equals(name, character.name) &&
                Objects.equals(description, character.description) &&
                Objects.equals(thumbnail, character.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
