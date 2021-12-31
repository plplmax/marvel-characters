package com.github.plplmax.mrv.data.local.source;

import com.github.plplmax.mrv.data.local.CharacterDao;
import com.github.plplmax.mrv.data.models.local.CharacterData;

import java.util.List;

public class CharactersLocalDataSourceImpl implements CharactersLocalDataSource {
    private final CharacterDao dao;

    public CharactersLocalDataSourceImpl(CharacterDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CharacterData> fetchCharacters() {
        return dao.fetchCharacters();
    }

    @Override
    public void saveCharacters(List<CharacterData> characters) {
        dao.saveCharacters(characters);
    }
}
