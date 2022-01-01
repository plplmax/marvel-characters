package com.github.plplmax.mrv.di;

import android.content.Context;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.mrv.ui.MainViewModelFactory;
import com.github.plplmax.mrv.ui.core.ResourceProvider;

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
    MainViewModelFactory provideMainViewModelFactory(FetchCharactersInteractor interactor) {
        return new MainViewModelFactory(interactor);
    }

    @Provides
    ResourceProvider provideResourceProvider(Context context) {
        return new ResourceProvider.Base(context);
    }
}
