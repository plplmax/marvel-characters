package com.github.plplmax.mrv.data.repository;

import com.github.plplmax.mrv.data.cloud.source.CharactersCloudDataSource;
import com.github.plplmax.mrv.data.local.source.CharactersLocalDataSource;
import com.github.plplmax.mrv.data.mappers.CharacterDataMapper;
import com.github.plplmax.mrv.data.mappers.CharacterDataWrapperResponseMapper;
import com.github.plplmax.mrv.data.models.local.CharacterData;
import com.github.plplmax.mrv.data.cloud.CharacterResponseMapper;
import com.github.plplmax.mrv.data.local.CharacterEntity;
import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersResult;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CharactersRepositoryImpl implements CharactersRepository {
    private final CharactersCloudDataSource cloudDataSource;
    private final CharactersLocalDataSource localDataSource;
    private final CharacterDataWrapperResponseMapper wrapperResponseMapper;
    private final CharacterDataMapper characterDataMapper;
    private final CharacterResponseMapper responseMapper;

    public CharactersRepositoryImpl(CharactersCloudDataSource cloudDataSource,
                                    CharactersLocalDataSource localDataSource,
                                    CharacterDataWrapperResponseMapper wrapperResponseMapper,
                                    CharacterDataMapper characterDataMapper) {
                                    CharacterResponseMapper responseMapper,
        this.cloudDataSource = cloudDataSource;
        this.localDataSource = localDataSource;
        this.wrapperResponseMapper = wrapperResponseMapper;
        this.characterDataMapper = characterDataMapper;
        this.responseMapper = responseMapper;
    }

    @Override
    public FetchCharactersResult fetchCharacters() {
        try {
            List<CharacterData> localResponse = localDataSource.fetchCharacters();
            List<CharacterEntity> localResponse = localDataSource.fetchCharacters();
            if (localResponse.isEmpty()) {
                Response<CharacterDataWrapperResponse> response = cloudDataSource.fetchCharacters();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Character> characters = wrapperResponseMapper.toCharactersList(response.body());
                        List<Character> characters = responseMapper.mapFromResponse(response.body());
                        saveCharacters(characters);
                        return new FetchCharactersResult.Success(characters);
                    }

                    return new FetchCharactersResult.Success(new ArrayList<>());
                }

                String errorMessage = response.errorBody() != null ? response.errorBody().string() : "";
                return new FetchCharactersResult.Fail(new Exception(errorMessage));
            } else {
                List<Character> characters = characterDataMapper.toCharactersList(localResponse);
                return new FetchCharactersResult.Success(characters);
            }
        } catch (Exception e) {
            return new FetchCharactersResult.Fail(e);
        }
    }

    @Override
    public void saveCharacters(List<Character> characters) {
        List<CharacterEntity> charactersData = characterEntityMapper.mapToEntity(characters);
        localDataSource.saveCharacters(charactersData);
    }
}
