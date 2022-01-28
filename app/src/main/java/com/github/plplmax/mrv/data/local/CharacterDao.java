package com.github.plplmax.mrv.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY name LIMIT 20 OFFSET :offset")
    List<CharacterEntity> fetchCharactersWithOffset(int offset);

    @Query("SELECT * FROM characters ORDER BY name LIMIT :limit")
    List<CharacterEntity> fetchCharactersWithLimit(int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCharacters(List<CharacterEntity> characters);
}