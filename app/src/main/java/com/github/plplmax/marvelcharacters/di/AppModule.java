package com.github.plplmax.marvelcharacters.di;

import android.content.Context;

import com.github.plplmax.marvelcharacters.domain.core.ErrorType;
import com.github.plplmax.marvelcharacters.domain.core.Mapper;
import com.github.plplmax.marvelcharacters.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.marvelcharacters.ui.characters.CharactersViewModelFactory;
import com.github.plplmax.marvelcharacters.ui.core.ErrorToUiMapper;
import com.github.plplmax.marvelcharacters.ui.core.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    public Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    CharactersViewModelFactory provideCharactersViewModelFactory(
            FetchCharactersInteractor interactor,
            Mapper<ErrorType, String> errorMapper) {
        return new CharactersViewModelFactory(interactor, errorMapper);
    }

    @Provides
    @Singleton
    ResourceProvider provideResourceProvider(Context context) {
        return new ResourceProvider.Base(context);
    }

    @Provides
    @Singleton
    Mapper<ErrorType, String> provideErrorToUiMapper(ResourceProvider provider) {
        return new ErrorToUiMapper(provider);
    }
}
