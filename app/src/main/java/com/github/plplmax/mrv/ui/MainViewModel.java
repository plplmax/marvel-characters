package com.github.plplmax.mrv.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {
    private final FetchCharactersInteractor interactor;
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(true);
    private final MutableLiveData<List<Character>> _success = new MutableLiveData<>();
    private final MutableLiveData<Exception> _fail = new MutableLiveData<>();

    public final List<Character> characters = new ArrayList<>();

    private boolean areAllCharactersLoaded = false;

    public final LiveData<Boolean> isLoading = _isLoading;
    public final LiveData<List<Character>> success = _success;
    public final LiveData<Exception> fail = _fail;

    public MainViewModel(FetchCharactersInteractor interactor) {
        this.interactor = interactor;
        fetchCharacters(0);
    }

    public void fetchCharacters(int offset) {
        if (offset == 0) _isLoading.setValue(true);

        service.execute(() -> {
            FetchCharactersResult result = interactor.Execute(offset);

            if (result instanceof FetchCharactersResult.Success) {
                final List<Character> characters = ((FetchCharactersResult.Success) result).getData();
                this.characters.addAll(characters);

                if (characters.isEmpty()) {
                    areAllCharactersLoaded = true;
                }

                _success.postValue(this.characters);
            } else if (result instanceof FetchCharactersResult.Fail) {
                _fail.postValue(((FetchCharactersResult.Fail) result).getException());
            }

            if (offset == 0) _isLoading.postValue(false);
        });
    }

    public boolean areAllCharactersLoaded() {
        return areAllCharactersLoaded;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        service.shutdownNow();
    }
}
