package com.github.plplmax.mrv.data.repository;

import com.github.plplmax.mrv.data.local.CharacterEntity;
import com.github.plplmax.mrv.data.local.CharacterEntityMapper;
import com.github.plplmax.mrv.data.local.CharactersLocalDataSource;
import com.github.plplmax.mrv.data.remote.CharacterResponseMapper;
import com.github.plplmax.mrv.data.remote.CharactersRemoteDataSource;
import com.github.plplmax.mrv.data.remote.responses.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersParams;
import com.github.plplmax.mrv.domain.models.FetchCharactersResult;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CharactersRepositoryImpl implements CharactersRepository {
    private final CharactersRemoteDataSource remoteDataSource;
    private final CharactersLocalDataSource localDataSource;
    private final CharacterResponseMapper responseMapper;
    private final CharacterEntityMapper characterEntityMapper;

    public CharactersRepositoryImpl(CharactersRemoteDataSource remoteDataSource,
                                    CharactersLocalDataSource localDataSource,
                                    CharacterResponseMapper responseMapper,
                                    CharacterEntityMapper characterEntityMapper) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.responseMapper = responseMapper;
        this.characterEntityMapper = characterEntityMapper;
    }

    @Override
    public FetchCharactersResult fetchCharacters(FetchCharactersParams params) {
        try {
            List<CharacterEntity> localResponse = localDataSource.fetchCharacters(params);

            if (localResponse.isEmpty()) {
                Response<CharacterDataWrapperResponse> response = remoteDataSource.fetchCharacters(params);

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Character> characters = responseMapper.mapFromResponse(response.body());
                        saveCharacters(characters);
                        return new FetchCharactersResult.Success(characters);
                    }

                    return new FetchCharactersResult.Success(new ArrayList<>());
                }

                String errorMessage = response.errorBody() != null ? response.errorBody().string() : "";
                return new FetchCharactersResult.Fail(new Exception(errorMessage));
            } else {
                List<Character> characters = characterEntityMapper.mapFromEntity(localResponse);
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
