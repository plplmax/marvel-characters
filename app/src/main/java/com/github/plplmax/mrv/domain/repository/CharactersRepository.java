package com.github.plplmax.mrv.domain.repository;

import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersResult;

import java.util.List;

public interface CharactersRepository {
    FetchCharactersResult fetchCharacters();

    void saveCharacters(List<Character> characters);
}
