package com.github.plplmax.mrv.di;

import com.github.plplmax.mrv.domain.core.Md5Provider;
import com.github.plplmax.mrv.domain.interactors.FetchCharactersWithOffsetInteractor;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    @Provides
    FetchCharactersWithOffsetInteractor provideFetchCharactersWithOffsetInteractor(CharactersRepository repository) {
        return new FetchCharactersWithOffsetInteractor(repository);
    }

    @Provides
    @Singleton
    Md5Provider provideMd5Provider() {
        return new Md5Provider.Base();
    }
}
