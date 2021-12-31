package com.github.plplmax.mrv.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.plplmax.mrv.data.models.local.CharacterData;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characters")
    List<CharacterData> fetchCharacters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCharacters(List<CharacterData> characterData);
}
