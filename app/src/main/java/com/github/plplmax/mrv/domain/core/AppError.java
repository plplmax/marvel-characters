package com.github.plplmax.mrv.domain.core;

public class AppError extends Exception {
    private final ErrorType errorType;

    public AppError(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType type() {
        return errorType;
    }
}
