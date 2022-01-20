package com.github.plplmax.mrv.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersWithOffsetInteractor;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final FetchCharactersWithOffsetInteractor fetchCharactersWithOffset;

    public MainViewModelFactory(FetchCharactersWithOffsetInteractor fetchCharactersWithOffset) {
        this.fetchCharactersWithOffset = fetchCharactersWithOffset;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(fetchCharactersWithOffset);
    }
}
