package com.github.plplmax.marvelcharacters.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CharacterEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();
}
