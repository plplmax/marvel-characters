package com.github.plplmax.mrv.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY name LIMIT :limit OFFSET :offset")
    Single<List<CharacterEntity>> fetchCharacters(int offset, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCharacters(List<CharacterEntity> characters);
}