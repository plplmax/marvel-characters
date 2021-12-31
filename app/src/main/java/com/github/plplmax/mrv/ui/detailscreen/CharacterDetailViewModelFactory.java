package com.github.plplmax.mrv.ui.detailscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.mrv.domain.models.Character;

public class CharacterDetailViewModelFactory implements ViewModelProvider.Factory {
    private final Character character;

    public CharacterDetailViewModelFactory(Character character) {
        this.character = character;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CharacterDetailViewModel(character);
    }
}
