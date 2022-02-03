package com.github.plplmax.mrv.data.repository;

import com.github.plplmax.mrv.data.local.CharacterEntity;
import com.github.plplmax.mrv.data.local.CharacterEntityMapper;
import com.github.plplmax.mrv.data.local.CharactersLocalDataSource;
import com.github.plplmax.mrv.data.remote.CharacterResponseMapper;
import com.github.plplmax.mrv.data.remote.CharactersRemoteDataSource;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.FetchCharactersParams;
import com.github.plplmax.mrv.domain.repository.CharactersRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

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
    public Single<List<Character>> fetchCharacters(FetchCharactersParams params) {
        return localDataSource.fetchCharacters(params)
                .flatMap(characterEntities -> {
                    if (characterEntities.isEmpty())
                        return remoteDataSource.fetchCharacters(params)
                                .map(responseMapper::mapFromResponse)
                                .doOnSuccess(this::saveCharacters);
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
