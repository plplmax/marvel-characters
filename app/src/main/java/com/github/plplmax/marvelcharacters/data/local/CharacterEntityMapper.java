package com.github.plplmax.marvelcharacters.data.local;

import androidx.annotation.NonNull;

import com.github.plplmax.marvelcharacters.data.core.EntityMapper;
import com.github.plplmax.marvelcharacters.domain.models.Character;
import com.github.plplmax.marvelcharacters.domain.models.Image;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterEntityMapper implements EntityMapper<List<CharacterEntity>, List<Character>> {
    @Override
    public List<CharacterEntity> mapToEntity(@NonNull List<Character> characters) {
        return characters.stream().map(character -> new CharacterEntity(
                character.getId(),
                character.getName(),
                character.getDescription(),
                new Image(character.getThumbnail().getPath(),
                        character.getThumbnail().getExtension())
        )).collect(Collectors.toList());
    }

    @Override
    public List<Character> mapFromEntity(@NonNull List<CharacterEntity> characters) {
        return characters.stream().map(character -> new Character(character.id,
                character.name,
                character.description,
                character.thumbnail)).collect(Collectors.toList());
    }
}
