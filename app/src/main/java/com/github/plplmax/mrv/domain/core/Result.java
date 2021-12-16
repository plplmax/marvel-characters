package com.github.plplmax.mrv.domain.core;

public interface Result {
    class Success<R> implements Result {
        private final R data;

        public Success(R data) {
            this.data = data;
        }

        public R getData() {
            return data;
        }
    }

    class Fail implements Result {
        private final Exception exception;

        public Fail(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}
