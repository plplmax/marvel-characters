package com.github.plplmax.mrv.data.local;

import androidx.annotation.NonNull;

import com.github.plplmax.mrv.domain.models.FetchCharactersParams;

import java.util.List;

public interface CharactersLocalDataSource {
    List<CharacterEntity> fetchCharacters(FetchCharactersParams params);

    void saveCharacters(List<CharacterEntity> characters);

    class Base implements CharactersLocalDataSource {
        private final CharacterDao dao;

        public Base(CharacterDao dao) {
            this.dao = dao;
        }

        @Override
        public List<CharacterEntity> fetchCharacters(@NonNull FetchCharactersParams params) {
            return dao.fetchCharacters(params.getOffset(), params.getLimit());
        }

        @Override
        public void saveCharacters(List<CharacterEntity> characters) {
            dao.saveCharacters(characters);
        }
    }
}
