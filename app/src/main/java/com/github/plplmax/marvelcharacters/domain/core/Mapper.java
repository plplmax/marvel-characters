package com.github.plplmax.marvelcharacters.domain.core;

public interface Mapper<S, R> {
    R map(S data);
}
