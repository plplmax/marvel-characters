package com.github.plplmax.mrv.data.cloud;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.datasource.CharactersCloudDataSource;

import retrofit2.Call;

public class CharactersCloudDataSourceImpl implements CharactersCloudDataSource {
    private final CharactersService service;

    public CharactersCloudDataSourceImpl(CharactersService service) {
        this.service = service;
    }

    @Override
    public Call<CharacterDataWrapperResponse> fetchCharacters() {
        return service.fetchCharacters();
    }
}
