package com.github.plplmax.mrv.data.local.source;

import com.github.plplmax.mrv.data.local.CharacterDao;
import com.github.plplmax.mrv.data.local.CharacterEntity;

import java.util.List;

public class CharactersLocalDataSourceImpl implements CharactersLocalDataSource {
    private final CharacterDao dao;

    public CharactersLocalDataSourceImpl(CharacterDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CharacterEntity> fetchCharactersWithOffset(int offset) {
        return dao.fetchCharactersWithOffset(offset);
    }

    @Override
    public void saveCharacters(List<CharacterEntity> characters) {
        dao.saveCharacters(characters);
    }
}
