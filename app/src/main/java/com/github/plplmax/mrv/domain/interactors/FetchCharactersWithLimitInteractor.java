package com.github.plplmax.mrv.domain.interactors;

import com.github.plplmax.mrv.domain.models.FetchCharactersResult;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

public class FetchCharactersWithLimitInteractor {
    private final CharactersRepository repository;

    public FetchCharactersWithLimitInteractor(CharactersRepository repository) {
        this.repository = repository;
    }

    public FetchCharactersResult Execute(int limit) {
        return repository.fetchCharactersWithLimit(limit);
    }
}
