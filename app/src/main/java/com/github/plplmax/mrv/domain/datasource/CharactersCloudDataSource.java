package com.github.plplmax.mrv.domain.datasource;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import retrofit2.Call;

public interface CharactersCloudDataSource {
    Call<CharacterDataWrapperResponse> fetchCharacters();
}
