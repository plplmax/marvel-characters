package com.github.plplmax.mrv.data.local;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.plplmax.mrv.data.core.EntityMapper;
import com.github.plplmax.mrv.domain.models.Character;
import com.github.plplmax.mrv.domain.models.Image;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharacterEntityMapperTest {
    private static List<Character> characters;
    private static List<CharacterEntity> characterEntities;

    @Before
    public void init() {
        final Image thumbnail = new Image("somePath", "jpg");

        final int firstCharacterId = 14;
        final String firstCharacterName = "A.I.M.";
        final String firstCharacterDescription = "aim description";

        final int secondCharacterId = 17;
        final String secondCharacterName = "Absorbing Man";
        final String secondCharacterDescription = "absorbing man description";

        characters = Arrays.asList(
                new Character(firstCharacterId,
                        firstCharacterName,
                        firstCharacterDescription,
                        thumbnail),
                new Character(secondCharacterId,
                        secondCharacterName,
                        secondCharacterDescription,
                        thumbnail)
        );

        characterEntities = Arrays.asList(
                new CharacterEntity(firstCharacterId,
                        firstCharacterName,
                        firstCharacterDescription,
                        thumbnail),
                new CharacterEntity(secondCharacterId,
                        secondCharacterName,
                        secondCharacterDescription,
                        thumbnail)
        );
    }

    @Test
    public void mapToEntity_twoCharacters_returnsTwoEntities() {
        EntityMapper<List<CharacterEntity>, List<Character>> mapper = new CharacterEntityMapper();

        List<CharacterEntity> results = mapper.mapToEntity(characters);

        assertThat(results, is(characterEntities));
    }

    @Test
    public void mapToEntity_emptyList_returnsEmptyList() {
        EntityMapper<List<CharacterEntity>, List<Character>> mapper = new CharacterEntityMapper();

        List<CharacterEntity> results = mapper.mapToEntity(Collections.emptyList());

        assertThat(results, is(Collections.emptyList()));
    }

    @Test
    public void mapFromEntity_twoEntities_returnsTwoCharacters() {
        EntityMapper<List<CharacterEntity>, List<Character>> mapper = new CharacterEntityMapper();

        List<Character> results = mapper.mapFromEntity(characterEntities);

        assertThat(results, is(characters));
    }

    @Test
    public void mapFromEntity_emptyList_returnsEmptyList() {
        EntityMapper<List<CharacterEntity>, List<Character>> mapper = new CharacterEntityMapper();

        List<Character> results = mapper.mapFromEntity(Collections.emptyList());

        assertThat(results, is(Collections.emptyList()));
    }
}