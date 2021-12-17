package com.github.plplmax.mrv.data.repository;

import com.github.plplmax.mrv.data.cloud.source.CharactersCloudDataSource;
import com.github.plplmax.mrv.data.mappers.CharacterDataWrapperResponseMapper;
import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersResult;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CharactersRepositoryImpl implements CharactersRepository {
    private final CharactersCloudDataSource cloudDataSource;
    private final CharacterDataWrapperResponseMapper mapper;

    public CharactersRepositoryImpl(CharactersCloudDataSource cloudDataSource,
                                    CharacterDataWrapperResponseMapper mapper) {
        this.cloudDataSource = cloudDataSource;
        this.mapper = mapper;
    }

    @Override
    public FetchCharactersResult fetchCharacters() {
        try {
            Response<CharacterDataWrapperResponse> response = cloudDataSource.fetchCharacters();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    List<Character> characters = mapper.toCharactersList(response.body());
                    return new FetchCharactersResult.Success(characters);
                }

                return new FetchCharactersResult.Success(new ArrayList<>());
            }

            return new FetchCharactersResult.Fail(new Exception());
        } catch (Exception e) {
            return new FetchCharactersResult.Fail(e);
        }
    }
}
