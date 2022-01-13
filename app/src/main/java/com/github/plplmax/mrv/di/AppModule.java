package com.github.plplmax.mrv.di;

import android.content.Context;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.mrv.ui.MainViewModelFactory;
import com.github.plplmax.mrv.ui.core.ResourceProvider;

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
    MainViewModelFactory provideMainViewModelFactory(FetchCharactersInteractor interactor) {
        return new MainViewModelFactory(interactor);
    }

    @Provides
    @Singleton
    ResourceProvider provideResourceProvider(Context context) {
        return new ResourceProvider.Base(context);
    }
}
