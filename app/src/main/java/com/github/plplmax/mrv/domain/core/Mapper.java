package com.github.plplmax.mrv.domain.core;

public interface Mapper<S, R> {
    R map(S data);
}
