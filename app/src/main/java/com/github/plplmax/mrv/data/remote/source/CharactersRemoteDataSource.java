package com.github.plplmax.mrv.data.remote.source;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;

import java.io.IOException;

import retrofit2.Response;

public interface CharactersRemoteDataSource {
    Response<CharacterDataWrapperResponse> fetchCharactersWithOffset(int offset) throws IOException;

    Response<CharacterDataWrapperResponse> fetchCharactersWithLimit(int limit) throws IOException;
}
