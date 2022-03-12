package com.github.plplmax.marvelcharacters.data.remote;

import com.github.plplmax.marvelcharacters.data.remote.responses.CharacterDataWrapperResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharactersService {
    @GET("characters")
    Single<CharacterDataWrapperResponse> fetchCharacters(@Query("offset") int offset,
                                                         @Query("limit") int limit);
}
