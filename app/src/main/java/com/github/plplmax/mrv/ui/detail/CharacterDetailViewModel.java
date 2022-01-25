package com.github.plplmax.mrv.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.plplmax.mrv.domain.models.Character;

public class CharacterDetailViewModel extends ViewModel {
    private final MutableLiveData<Character> _character = new MutableLiveData<>();
    public final LiveData<Character> character = _character;

    public CharacterDetailViewModel(Character character) {
        this._character.setValue(character);
    }
}
