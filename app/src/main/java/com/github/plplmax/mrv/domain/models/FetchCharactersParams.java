package com.github.plplmax.mrv.domain.models;

public class FetchCharactersParams {
    private int offset;
    private int limit;

    public FetchCharactersParams(int offset, int limit) {
        setOffset(offset);
        setLimit(limit);
    }

    public int getOffset() {
        return offset;
    }

    private void setOffset(int offset) {
        if (offset < 0) throw new IllegalArgumentException("offset must not be negative");
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    private void setLimit(int limit) {
        if (limit <= 0) throw new IllegalArgumentException("limit must be greater than 0");
        this.limit = limit;
    }
}
