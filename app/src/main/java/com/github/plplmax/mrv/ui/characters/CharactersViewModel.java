package com.github.plplmax.mrv.ui.characters;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersParams;
import com.github.plplmax.mrv.domain.models.FetchCharactersResult;
import com.github.plplmax.mrv.ui.core.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CharactersViewModel extends ViewModel {
    private static final int DEFAULT_CHARACTERS_OFFSET = 0;
    private static final int DEFAULT_CHARACTERS_LIMIT = 20;

    private final ExecutorService service = Executors.newSingleThreadExecutor();

    private final FetchCharactersInteractor interactor;

    private boolean onScrolledActive = true;
    private boolean areAllCharactersLoaded = false;

    private final MutableLiveData<State> _state = new MutableLiveData<>();
    public final LiveData<State> state = _state;

    public final List<Character> characters = new ArrayList<>();

    public int lastCharactersLoadedCount = 0;
    public String failMessage;

    public CharactersViewModel(FetchCharactersInteractor interactor) {
        this.interactor = interactor;
    }

    public void fetchCharactersWithOffset(int offset) {
        _state.setValue(State.LOADING);

        service.execute(() -> {
            FetchCharactersParams params = new FetchCharactersParams(offset, DEFAULT_CHARACTERS_LIMIT);
            FetchCharactersResult result = interactor.Execute(params);

            if (result instanceof FetchCharactersResult.Success) {
                final List<Character> characters = ((FetchCharactersResult.Success) result).getData();
                this.characters.addAll(characters);
                updateLastCharactersLoadedCount();

                if (characters.isEmpty()) {
                    areAllCharactersLoaded = true;
                }

                _state.postValue(State.DONE);
            } else if (result instanceof FetchCharactersResult.Fail) {
                failMessage = ((FetchCharactersResult.Fail) result).getException().getMessage();
                _state.postValue(State.ERROR);
            }
        });
    }

    public void fetchCharactersWithLimit(int limit) {
        _state.setValue(State.LOADING);

        service.execute(() -> {
            FetchCharactersParams params = new FetchCharactersParams(DEFAULT_CHARACTERS_OFFSET, limit);
            FetchCharactersResult result = interactor.Execute(params);

            if (result instanceof FetchCharactersResult.Success) {
                final List<Character> characters = ((FetchCharactersResult.Success) result).getData();
                this.characters.addAll(characters);
                updateLastCharactersLoadedCount();

                if (characters.isEmpty()) {
                    fetchCharactersWithOffset(0);
                    return;
                }

                _state.postValue(State.DONE);
            } else if (result instanceof FetchCharactersResult.Fail) {
                failMessage = ((FetchCharactersResult.Fail) result).getException().getMessage();
                _state.postValue(State.ERROR);
            }
        });
    }

    public void restoreCharacters() {
        if (!areCharactersEmpty()) return;

        if (lastCharactersLoadedCount == 0) fetchCharactersWithOffset(lastCharactersLoadedCount);
        else fetchCharactersWithLimit(lastCharactersLoadedCount);
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
        super.onCleared();
        service.shutdownNow();
        Log.e("TEST", "onCleared: service is shutdown - " + service.isTerminated() );
        Log.d("TEST", "onCleared()");
    }
}
