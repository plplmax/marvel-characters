package com.github.plplmax.mrv.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.plplmax.mrv.data.models.local.CharacterData;

@Database(entities = {CharacterData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();
}
