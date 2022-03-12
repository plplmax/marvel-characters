package com.github.plplmax.marvelcharacters.data.local;

import androidx.annotation.NonNull;

import com.github.plplmax.marvelcharacters.domain.models.FetchCharactersParams;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CharactersLocalDataSource {
    Single<List<CharacterEntity>> fetchCharacters(FetchCharactersParams params);

    void saveCharacters(List<CharacterEntity> characters);

    class Base implements CharactersLocalDataSource {
        private final CharacterDao dao;

        public Base(CharacterDao dao) {
            this.dao = dao;
        }

        @Override
        public Single<List<CharacterEntity>> fetchCharacters(@NonNull FetchCharactersParams params) {
            return dao.fetchCharacters(params.getOffset(), params.getLimit());
        }

        @Override
        public void saveCharacters(List<CharacterEntity> characters) {
            dao.saveCharacters(characters);
        }
    }
}
