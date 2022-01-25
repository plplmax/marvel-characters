package com.github.plplmax.mrv.di;

import com.github.plplmax.mrv.ui.characters.CharactersFragment;
import com.github.plplmax.mrv.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DomainModule.class, DataModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(CharactersFragment charactersFragment);
}
