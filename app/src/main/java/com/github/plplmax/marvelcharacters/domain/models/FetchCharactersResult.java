package com.github.plplmax.marvelcharacters.domain.models;

import java.util.List;

public interface FetchCharactersResult {
    class Success implements FetchCharactersResult {
        private final List<Character> data;

        public Success(List<Character> data) {
            this.data = data;
        }

        public List<Character> getData() {
            return data;
        }
    }

    class Fail implements FetchCharactersResult {
        private final Exception exception;

        public Fail(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}
