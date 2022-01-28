package com.github.plplmax.mrv.data.remote;

import androidx.annotation.NonNull;

import com.github.plplmax.mrv.data.remote.responses.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.models.FetchCharactersParams;

import java.io.IOException;

import retrofit2.Response;

public interface CharactersRemoteDataSource {
    Response<CharacterDataWrapperResponse> fetchCharacters(FetchCharactersParams params)
            throws IOException;

    class Base implements CharactersRemoteDataSource {
        private final CharactersService service;

        public Base(CharactersService service) {
            this.service = service;
        }

        @Override
        public Response<CharacterDataWrapperResponse> fetchCharacters(
                @NonNull FetchCharactersParams params
        ) throws IOException {
            return service.fetchCharacters(params.getOffset(), params.getLimit()).execute();
        }
    }
}
