package com.github.plplmax.mrv.data.cloud.source;

import com.github.plplmax.mrv.data.cloud.CharactersService;
import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import java.io.IOException;

import retrofit2.Response;

public class CharactersCloudDataSourceImpl implements CharactersCloudDataSource {
    private final CharactersService service;

    public CharactersCloudDataSourceImpl(CharactersService service) {
        this.service = service;
    }

    @Override
    public Response<CharacterDataWrapperResponse> fetchCharacters(int offset) throws IOException {
        return service.fetchCharacters(offset).execute();
    }
}
