package com.github.plplmax.marvelcharacters.data.local;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.plplmax.marvelcharacters.domain.models.Image;

import java.util.Objects;

@Entity(tableName = "characters")
public class CharacterEntity {
    @PrimaryKey
    public int id;
    public String name;
    public String description;
    @Embedded
    public Image thumbnail;

    public CharacterEntity(int id, String name, String description, Image thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterEntity that = (CharacterEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(thumbnail, that.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
