package com.github.plplmax.mrv.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY name LIMIT :limit OFFSET :offset")
    List<CharacterEntity> fetchCharacters(int offset, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCharacters(List<CharacterEntity> characters);
}