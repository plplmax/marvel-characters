package com.github.plplmax.mrv.data.cloud;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.datasource.CharactersCloudDataSource;

import retrofit2.Response;

public class CharactersCloudDataSourceImpl implements CharactersCloudDataSource {
    private final CharactersService service;

    public CharactersCloudDataSourceImpl(CharactersService service) {
        this.service = service;
    }

    @Override
    public Response<CharacterDataWrapperResponse> fetchCharacters() {
        return service.fetchCharacters();
    }
}
