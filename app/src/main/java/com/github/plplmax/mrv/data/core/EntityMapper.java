package com.github.plplmax.mrv.data.core;

public interface EntityMapper<E, M> {
    E mapToEntity(M data);
    M mapFromEntity(E data);
}
