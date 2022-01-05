package com.github.plplmax.mrv.data.local;

import androidx.annotation.NonNull;

import com.github.plplmax.mrv.data.core.EntityMapper;
import com.github.plplmax.mrv.data.local.CharacterEntity;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.Image;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterEntityMapper implements EntityMapper<List<CharacterEntity>, List<Character>> {
    @Override
    public List<CharacterEntity> mapToEntity(@NonNull List<Character> characters) {
        return characters.stream().map(character -> {
            CharacterEntity characterEntity = new CharacterEntity();
            characterEntity.id = character.getId();
            characterEntity.name = character.getName();
            characterEntity.description = character.getDescription();
            characterEntity.thumbnail = new Image(character.getThumbnail().getPath(),
                    character.getThumbnail().getExtension());
            return characterEntity;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Character> mapFromEntity(@NonNull List<CharacterEntity> characters) {
        return characters.stream().map(character -> new Character(character.id,
                character.name,
                character.description,
                character.thumbnail)).collect(Collectors.toList());
    }
}
