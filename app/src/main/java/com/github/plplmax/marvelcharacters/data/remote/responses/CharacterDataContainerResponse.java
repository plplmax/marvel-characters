package com.github.plplmax.marvelcharacters.data.remote.responses;

import java.util.List;

public class CharacterDataContainerResponse {
    public List<CharacterResponse> results;

    public CharacterDataContainerResponse(List<CharacterResponse> results) {
        this.results = results;
    }
}