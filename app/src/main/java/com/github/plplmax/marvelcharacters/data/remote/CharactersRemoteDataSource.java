package com.github.plplmax.marvelcharacters.data.remote;

import androidx.annotation.NonNull;

import com.github.plplmax.marvelcharacters.data.remote.responses.CharacterDataWrapperResponse;
import com.github.plplmax.marvelcharacters.domain.models.FetchCharactersParams;

import io.reactivex.rxjava3.core.Single;

public interface CharactersRemoteDataSource {
    Single<CharacterDataWrapperResponse> fetchCharacters(FetchCharactersParams params);

    class Base implements CharactersRemoteDataSource {
        private final CharactersService service;

        public Base(CharactersService service) {
            this.service = service;
        }

        @Override
        public Single<CharacterDataWrapperResponse> fetchCharacters(
                @NonNull FetchCharactersParams params
        ) {
            return service.fetchCharacters(params.getOffset(), params.getLimit());
        }
    }
}
