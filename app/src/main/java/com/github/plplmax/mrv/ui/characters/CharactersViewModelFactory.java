package com.github.plplmax.mrv.ui.characters;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.domain.core.ErrorType;
import com.github.plplmax.mrv.domain.core.Mapper;
import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;

public class CharactersViewModelFactory implements ViewModelProvider.Factory {
    private final FetchCharactersInteractor interactor;
    private final Mapper<ErrorType, String> errorMapper;

    public CharactersViewModelFactory(
            FetchCharactersInteractor interactor,
            Mapper<ErrorType, String> errorMapper) {
        this.interactor = interactor;
        this.errorMapper = errorMapper;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CharactersViewModel(interactor, errorMapper);
    }
}
