package com.github.plplmax.marvelcharacters.data.core;

public interface ResponseMapper<R, M> {
    M mapFromResponse(R data);
}
