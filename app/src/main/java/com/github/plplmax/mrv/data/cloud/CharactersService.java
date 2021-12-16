package com.github.plplmax.mrv.data.cloud;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import retrofit2.Response;
import retrofit2.http.GET;

public interface CharactersService {
    @GET("characters")
    Response<CharacterDataWrapperResponse> fetchCharacters();
}
