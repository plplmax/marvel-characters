package com.github.plplmax.mrv.data.cloud;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.plplmax.mrv.data.core.ResponseMapper;
import com.github.plplmax.mrv.data.models.network.CharacterDataContainerResponse;
import com.github.plplmax.mrv.data.models.network.CharacterDataWrapperResponse;
import com.github.plplmax.mrv.data.models.network.CharacterResponse;
import com.github.plplmax.mrv.data.models.network.ImageResponse;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.Image;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class CharacterResponseMapperTest {
    private static final int FAKE_CHARACTER_ID = 173;
    private static final int FAKE_RESPONSE_CODE = 200;
    private static final String FAKE_RESPONSE_MESSAGE = "ok";

    private static final String FAKE_CHARACTER_NAME = "first character";
    private static final String FAKE_CHARACTER_DESCRIPTION = "First character description";
    private static final String FAKE_IMAGE_PATH = "somePath";
    private static final String FAKE_IMAGE_EXTENSION = "jpg";

    @Test
    public void mapFromResponse_fiveResponses_returnsFiveCharacters() {
        ResponseMapper<CharacterDataWrapperResponse, List<Character>> mapper =
                new CharacterResponseMapper();

        List<CharacterResponse> characterResponses = Collections.nCopies(5, new CharacterResponse(
                FAKE_CHARACTER_ID,
                FAKE_CHARACTER_NAME,
                FAKE_CHARACTER_DESCRIPTION,
                new ImageResponse(FAKE_IMAGE_PATH, FAKE_IMAGE_EXTENSION)));

        CharacterDataWrapperResponse wrapperResponse = new CharacterDataWrapperResponse(
                FAKE_RESPONSE_CODE,
                FAKE_RESPONSE_MESSAGE,
                new CharacterDataContainerResponse(characterResponses)
        );

        List<Character> expected = Collections.nCopies(5, new Character(
                FAKE_CHARACTER_ID,
                FAKE_CHARACTER_NAME,
                FAKE_CHARACTER_DESCRIPTION,
                new Image(FAKE_IMAGE_PATH, FAKE_IMAGE_EXTENSION)
        ));

        List<Character> results = mapper.mapFromResponse(wrapperResponse);

        assertThat(results, is(expected));
    }

    @Test
    public void mapFromResponse_emptyList_returnsEmptyList() {
        ResponseMapper<CharacterDataWrapperResponse, List<Character>> mapper =
                new CharacterResponseMapper();

        CharacterDataWrapperResponse wrapperResponse = new CharacterDataWrapperResponse(
                FAKE_RESPONSE_CODE,
                FAKE_RESPONSE_MESSAGE,
                new CharacterDataContainerResponse(Collections.emptyList())
        );

        List<Character> results = mapper.mapFromResponse(wrapperResponse);

        assertThat(results, is(Collections.emptyList()));
    }
}