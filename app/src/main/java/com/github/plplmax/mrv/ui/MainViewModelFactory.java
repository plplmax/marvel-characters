package com.github.plplmax.mrv.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final FetchCharactersInteractor interactor;

    public MainViewModelFactory(FetchCharactersInteractor interactor) {
        this.interactor = interactor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(interactor);
    }
}
