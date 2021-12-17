package com.github.plplmax.mrv.domain.interactors;

import com.github.plplmax.mrv.domain.models.FetchCharactersResult;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

public class FetchCharactersInteractor {
    private final CharactersRepository repository;

    public FetchCharactersInteractor(CharactersRepository repository) {
        this.repository = repository;
    }

    public FetchCharactersResult Execute() {
        return repository.fetchCharacters();
    }
}
