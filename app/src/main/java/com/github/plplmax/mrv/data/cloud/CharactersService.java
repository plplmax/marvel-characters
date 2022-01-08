package com.github.plplmax.mrv.data.cloud;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharactersService {
    @GET("characters?limit=20")
    Call<CharacterDataWrapperResponse> fetchCharacters(@Query("offset") int offset);
}
