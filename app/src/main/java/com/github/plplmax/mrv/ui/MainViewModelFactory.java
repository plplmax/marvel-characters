package com.github.plplmax.mrv.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersWithLimitInteractor;
import com.github.plplmax.mrv.domain.interactors.FetchCharactersWithOffsetInteractor;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final FetchCharactersWithOffsetInteractor fetchCharactersWithOffset;
    private final FetchCharactersWithLimitInteractor fetchCharactersWithLimit;

    public MainViewModelFactory(FetchCharactersWithOffsetInteractor fetchCharactersWithOffset,
                                FetchCharactersWithLimitInteractor fetchCharactersWithLimit) {
        this.fetchCharactersWithOffset = fetchCharactersWithOffset;
        this.fetchCharactersWithLimit = fetchCharactersWithLimit;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(fetchCharactersWithOffset, fetchCharactersWithLimit);
    }
}
