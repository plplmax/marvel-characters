package com.github.plplmax.mrv.ui.characters;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;

public class CharactersViewModelFactory implements ViewModelProvider.Factory {
    private final FetchCharactersInteractor interactor;

    public CharactersViewModelFactory(FetchCharactersInteractor interactor) {
        this.interactor = interactor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CharactersViewModel(interactor);
    }
}
