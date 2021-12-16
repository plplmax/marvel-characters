package com.github.plplmax.mrv.data.cloud.source;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import retrofit2.Response;

public interface CharactersCloudDataSource {
    Response<CharacterDataWrapperResponse> fetchCharacters();
}
