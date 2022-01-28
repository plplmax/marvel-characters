package com.github.plplmax.mrv.data.remote;

import com.github.plplmax.mrv.data.remote.responses.CharacterDataWrapperResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharactersService {
    @GET("characters")
    Call<CharacterDataWrapperResponse> fetchCharacters(@Query("offset") int offset,
                                                       @Query("limit") int limit);
}
