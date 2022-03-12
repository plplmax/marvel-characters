package com.github.plplmax.marvelcharacters.domain.interactors;

import com.github.plplmax.marvelcharacters.domain.models.Character;
import com.github.plplmax.marvelcharacters.domain.models.FetchCharactersParams;
import com.github.plplmax.marvelcharacters.domain.repository.CharactersRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class FetchCharactersInteractor {
    private final CharactersRepository repository;

    public FetchCharactersInteractor(CharactersRepository repository) {
        this.repository = repository;
    }

    public Single<List<Character>> Execute(FetchCharactersParams params) {
        return repository.fetchCharacters(params);
    }
}
