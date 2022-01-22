package com.github.plplmax.mrv.domain.interactors;

import com.github.plplmax.mrv.domain.models.FetchCharactersResult;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

public class FetchCharactersWithOffsetInteractor {
    private final CharactersRepository repository;

    public FetchCharactersWithOffsetInteractor(CharactersRepository repository) {
        this.repository = repository;
    }

    public FetchCharactersResult Execute(int offset) {
        return repository.fetchCharactersWithOffset(offset);
    }
}
