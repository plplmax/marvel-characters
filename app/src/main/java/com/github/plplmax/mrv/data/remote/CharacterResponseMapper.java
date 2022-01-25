package com.github.plplmax.mrv.data.remote;

import androidx.annotation.NonNull;

import com.github.plplmax.mrv.data.core.ResponseMapper;
import com.github.plplmax.mrv.data.remote.responses.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.Image;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterResponseMapper
        implements ResponseMapper<CharacterDataWrapperResponse, List<Character>> {
    @Override
    public List<Character> mapFromResponse(@NonNull CharacterDataWrapperResponse response) {
        return response.data.results.stream().map(item -> {
            Image image = new Image(item.thumbnail.path, item.thumbnail.extension);
            return new Character(item.id, item.name, item.description, image);
        }).collect(Collectors.toList());
    }
}
