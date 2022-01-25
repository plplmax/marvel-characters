package com.github.plplmax.mrv.data.remote;

import com.github.plplmax.mrv.data.remote.responses.CharacterDataWrapperResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharactersService {
    @GET("characters?limit=20")
    Call<CharacterDataWrapperResponse> fetchCharactersWithOffset(@Query("offset") int offset);

    @GET("characters")
    Call<CharacterDataWrapperResponse> fetchCharactersWithLimit(@Query("limit") int limit);
}
