package com.github.plplmax.marvelcharacters.data.core;

public interface EntityMapper<E, M> {
    E mapToEntity(M data);
    M mapFromEntity(E data);
}
