package com.github.plplmax.marvelcharacters.di;

import com.github.plplmax.marvelcharacters.domain.core.Md5Provider;
import com.github.plplmax.marvelcharacters.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.marvelcharacters.domain.repository.CharactersRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    @Provides
    FetchCharactersInteractor provideFetchCharactersInteractor(CharactersRepository repository) {
        return new FetchCharactersInteractor(repository);
    }

    @Provides
    @Singleton
    Md5Provider provideMd5Provider() {
        return new Md5Provider.Base();
    }
}
