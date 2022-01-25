package com.github.plplmax.mrv.data.remote.source;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.data.remote.CharactersService;

import java.io.IOException;

import retrofit2.Response;

public class CharactersRemoteDataSourceImpl implements CharactersRemoteDataSource {
    private final CharactersService service;

    public CharactersRemoteDataSourceImpl(CharactersService service) {
        this.service = service;
    }

    @Override
    public Response<CharacterDataWrapperResponse> fetchCharactersWithOffset(int offset) throws IOException {
        return service.fetchCharactersWithOffset(offset).execute();
    }

    @Override
    public Response<CharacterDataWrapperResponse> fetchCharactersWithLimit(int limit) throws IOException {
        return service.fetchCharactersWithLimit(limit).execute();
    }
}
