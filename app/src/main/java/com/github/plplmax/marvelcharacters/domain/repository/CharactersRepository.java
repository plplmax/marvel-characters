package com.github.plplmax.marvelcharacters.domain.repository;

import com.github.plplmax.marvelcharacters.domain.models.Character;
import com.github.plplmax.marvelcharacters.domain.models.FetchCharactersParams;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CharactersRepository {
    Single<List<Character>> fetchCharacters(FetchCharactersParams params);

    void saveCharacters(List<Character> characters);
}
