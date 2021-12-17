package com.github.plplmax.mrv.domain.core;

public interface Result<R> {

    class Success<S> implements Result<S> {
        private final S data;

        public Success(S data) {
            this.data = data;
        }

        public S getData() {
            return data;
        }
    }

    class Fail<F> implements Result<F> {
        private final F exception;

        public Fail(F exception) {
            this.exception = exception;
        }

        public F getException() {
            return exception;
        }
    }
}
