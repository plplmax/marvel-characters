package com.github.plplmax.mrv.data.local.source;

import com.github.plplmax.mrv.data.models.local.CharacterData;

import java.util.List;

public interface CharactersLocalDataSource {
    List<CharacterData> fetchCharacters();

    void saveCharacters(List<CharacterData> characters);
}
