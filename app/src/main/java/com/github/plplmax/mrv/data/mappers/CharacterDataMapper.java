package com.github.plplmax.mrv.data.mappers;

import androidx.annotation.NonNull;

import com.github.plplmax.mrv.data.models.local.CharacterData;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.Image;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterDataMapper {
    public List<Character> toCharactersList(@NonNull List<CharacterData> characters) {
        return characters.stream().map(character -> new Character(character.id,
                character.name,
                character.description,
                character.thumbnail)).collect(Collectors.toList());
    }

    public List<CharacterData> toCharactersDataList(@NonNull List<Character> characters) {
        return characters.stream().map(character -> {
            CharacterData characterData = new CharacterData();
            characterData.id = character.getId();
            characterData.name = character.getName();
            characterData.description = character.getDescription();
            characterData.thumbnail = new Image(character.getThumbnail().getPath(),
                    character.getThumbnail().getExtension());
            return characterData;
        }).collect(Collectors.toList());
    }
}
