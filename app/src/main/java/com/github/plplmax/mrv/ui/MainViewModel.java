package com.github.plplmax.mrv.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.plplmax.mrv.domain.interactors.FetchCharactersInteractor;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersResult;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {
    private final FetchCharactersInteractor interactor;
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<List<Character>> _success = new MutableLiveData<>();
    private final MutableLiveData<Exception> _fail = new MutableLiveData<>();
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    public LiveData<Boolean> isLoading = _isLoading;
    public LiveData<List<Character>> success = _success;
    public LiveData<Exception> fail = _fail;

    public MainViewModel(FetchCharactersInteractor interactor) {
        this.interactor = interactor;
        fetchCharacters();
    }

    public void fetchCharacters() {
        _isLoading.setValue(true);
        service.execute(() -> {
            FetchCharactersResult result = interactor.Execute();
            if (result instanceof FetchCharactersResult.Success) {
                _success.postValue(((FetchCharactersResult.Success) result).getData());
            } else if (result instanceof FetchCharactersResult.Fail) {
                _fail.postValue(((FetchCharactersResult.Fail) result).getException());
            }
            _isLoading.postValue(false);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        service.shutdownNow();
    }
}
