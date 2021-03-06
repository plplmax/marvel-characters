package com.github.plplmax.marvelcharacters.ui.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.plplmax.marvelcharacters.domain.core.AppError;
import com.github.plplmax.marvelcharacters.domain.core.ErrorType;
import com.github.plplmax.marvelcharacters.domain.core.Mapper;
import com.github.plplmax.marvelcharacters.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.marvelcharacters.domain.models.Character;
import com.github.plplmax.marvelcharacters.domain.models.FetchCharactersParams;
import com.github.plplmax.marvelcharacters.ui.core.State;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CharactersViewModel extends ViewModel {
    private static final int DEFAULT_CHARACTERS_OFFSET = 0;
    protected static final int DEFAULT_CHARACTERS_LIMIT = 20;

    private final FetchCharactersInteractor interactor;
    private final Mapper<ErrorType, String> errorMapper;

    private final MutableLiveData<State> _state = new MutableLiveData<>();
    public final LiveData<State> state = _state;

    private Disposable disposable;
    private boolean onScrolledActive = true;
    private boolean areAllCharactersLoaded = false;

    public List<Character> characters = new ArrayList<>();
    public String failMessage;
    public int lastCharactersLoadedCount = 0;

    public CharactersViewModel(
            FetchCharactersInteractor interactor,
            Mapper<ErrorType, String> errorMapper) {
        this.interactor = interactor;
        this.errorMapper = errorMapper;
    }

    public void fetchCharacters(FetchCharactersParams params) {
        _state.setValue(State.LOADING);

        disposable = interactor.Execute(params)
                .subscribeOn(Schedulers.io())
                .subscribe(newCharacters -> {
                            characters.addAll(newCharacters);
                            updateLastCharactersLoadedCount();

                            if (newCharacters.isEmpty()) areAllCharactersLoaded = true;
                            _state.postValue(State.DONE);
                        },
                        error -> {
                            ErrorType errorType = ((AppError) error).type();
                            failMessage = errorMapper.map(errorType);
                            _state.postValue(State.ERROR);
                        });
    }

    public void restoreCharacters() {
        if (!areCharactersEmpty()) return;

        FetchCharactersParams params = lastCharactersLoadedCount == 0 ?
                new FetchCharactersParams(DEFAULT_CHARACTERS_OFFSET, DEFAULT_CHARACTERS_LIMIT) :
                new FetchCharactersParams(DEFAULT_CHARACTERS_OFFSET, lastCharactersLoadedCount);

        fetchCharacters(params);
    }

    public boolean isOnScrolledActive() {
        return onScrolledActive && !areAllCharactersLoaded;
    }

    public void activateOnScrolled() {
        onScrolledActive = true;
    }

    public void deactivateOnScrolled() {
        onScrolledActive = false;
    }

    public boolean areCharactersEmpty() {
        return characters.isEmpty();
    }

    public int fetchCharactersCount() {
        return characters.size();
    }

    private void updateLastCharactersLoadedCount() {
        lastCharactersLoadedCount = fetchCharactersCount();
    }

    @Override
    protected void onCleared() {
        if (disposable != null) disposable.dispose();
        disposable = null;

        super.onCleared();
    }
}
