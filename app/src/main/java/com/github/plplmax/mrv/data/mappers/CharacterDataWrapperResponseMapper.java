package com.github.plplmax.mrv.data.mappers;

import androidx.annotation.NonNull;

import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.Image;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterDataWrapperResponseMapper {
    public List<Character> toCharactersList(@NonNull CharacterDataWrapperResponse response) {
        return response.data.results.stream().map(item -> {
            Image image = new Image(item.thumbnail.path, item.thumbnail.extension);
            return new Character(item.id, item.name, item.description, image);
        }).collect(Collectors.toList());
    }
}
