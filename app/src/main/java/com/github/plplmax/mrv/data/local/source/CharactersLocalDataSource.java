package com.github.plplmax.mrv.data.local.source;

import com.github.plplmax.mrv.data.local.CharacterEntity;

import java.util.List;

public interface CharactersLocalDataSource {
    List<CharacterEntity> fetchCharacters();

    void saveCharacters(List<CharacterEntity> characters);
}
