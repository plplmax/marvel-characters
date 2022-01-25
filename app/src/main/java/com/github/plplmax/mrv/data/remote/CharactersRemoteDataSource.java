package com.github.plplmax.mrv.data.remote;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import java.io.IOException;

import retrofit2.Response;

public interface CharactersRemoteDataSource {
    Response<CharacterDataWrapperResponse> fetchCharactersWithOffset(int offset) throws IOException;

    Response<CharacterDataWrapperResponse> fetchCharactersWithLimit(int limit) throws IOException;

    class Base implements CharactersRemoteDataSource {
        private final CharactersService service;

        public Base(CharactersService service) {
            this.service = service;
        }

        @Override
        public Response<CharacterDataWrapperResponse> fetchCharactersWithOffset(int offset)
                throws IOException {
            return service.fetchCharactersWithOffset(offset).execute();
        }

        @Override
        public Response<CharacterDataWrapperResponse> fetchCharactersWithLimit(int limit)
                throws IOException {
            return service.fetchCharactersWithLimit(limit).execute();
        }
    }
}
