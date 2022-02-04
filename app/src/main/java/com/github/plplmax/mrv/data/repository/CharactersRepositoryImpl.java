package com.github.plplmax.mrv.data.repository;

import com.github.plplmax.mrv.data.core.EntityMapper;
import com.github.plplmax.mrv.data.core.ResponseMapper;
import com.github.plplmax.mrv.data.local.CharacterEntity;
import com.github.plplmax.mrv.data.local.CharactersLocalDataSource;
import com.github.plplmax.mrv.data.remote.CharactersRemoteDataSource;
import com.github.plplmax.mrv.data.remote.responses.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.core.AppError;
import com.github.plplmax.mrv.domain.core.Mapper;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersParams;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CharactersRepositoryImpl implements CharactersRepository {
    private final CharactersRemoteDataSource remoteDataSource;
    private final CharactersLocalDataSource localDataSource;
    private final ResponseMapper<CharacterDataWrapperResponse, List<Character>> responseMapper;
    private final EntityMapper<List<CharacterEntity>, List<Character>> characterEntityMapper;
    private final Mapper<Throwable, AppError> errorToDomainMapper;

    public CharactersRepositoryImpl(
            CharactersRemoteDataSource remoteDataSource,
            CharactersLocalDataSource localDataSource,
            ResponseMapper<CharacterDataWrapperResponse, List<Character>> responseMapper,
            EntityMapper<List<CharacterEntity>, List<Character>> characterEntityMapper,
            Mapper<Throwable, AppError> errorToDomainMapper) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.responseMapper = responseMapper;
        this.characterEntityMapper = characterEntityMapper;
        this.errorToDomainMapper = errorToDomainMapper;
    }

    @Override
    public Single<List<Character>> fetchCharacters(FetchCharactersParams params) {
        return localDataSource.fetchCharacters(params)
                .flatMap(characterEntities -> {
                    if (characterEntities.isEmpty())
                        return remoteDataSource.fetchCharacters(params)
                                .map(responseMapper::mapFromResponse)
                                .doOnSuccess(this::saveCharacters)
                                .onErrorResumeNext(throwable -> {
                                    AppError appError = errorToDomainMapper.map(throwable);
                                    return Single.error(appError);
                                });
                    else return Single.just(characterEntities)
                            .map(characterEntityMapper::mapFromEntity);
                });
    }

    @Override
    public void saveCharacters(List<Character> characters) {
        List<CharacterEntity> charactersData = characterEntityMapper.mapToEntity(characters);
        localDataSource.saveCharacters(charactersData);
    }
}
