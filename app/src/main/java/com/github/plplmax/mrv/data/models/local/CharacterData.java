package com.github.plplmax.mrv.data.models.local;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.plplmax.mrv.domain.models.Image;

@Entity(tableName = "characters")
public class CharacterData {
    @PrimaryKey
    public int id;
    public String name;
    public String description;
    @Embedded
    public Image thumbnail;
}
