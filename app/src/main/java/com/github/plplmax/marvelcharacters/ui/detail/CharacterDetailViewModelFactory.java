package com.github.plplmax.marvelcharacters.ui.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.plplmax.marvelcharacters.domain.models.Character;

public class CharacterDetailViewModelFactory implements ViewModelProvider.Factory {
    private final Character character;

    public CharacterDetailViewModelFactory(Character character) {
        this.character = character;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CharacterDetailViewModel(character);
    }
}
