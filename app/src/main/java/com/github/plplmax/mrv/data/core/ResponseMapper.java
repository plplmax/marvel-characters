package com.github.plplmax.mrv.data.core;

public interface ResponseMapper<R, M> {
    M mapFromResponse(R data);
}
