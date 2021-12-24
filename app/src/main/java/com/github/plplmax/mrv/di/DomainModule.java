package com.github.plplmax.mrv.di;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    @Provides
    FetchCharactersInteractor provideFetchCharactersInteractor(CharactersRepository repository) {
        return new FetchCharactersInteractor(repository);
    }
}
