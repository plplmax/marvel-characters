package com.github.plplmax.mrv.domain.datasource;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import retrofit2.Response;

public interface CharactersCloudDataSource {
    Response<CharacterDataWrapperResponse> fetchCharacters();
}
