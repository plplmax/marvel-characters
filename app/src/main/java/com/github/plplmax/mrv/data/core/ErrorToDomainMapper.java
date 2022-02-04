package com.github.plplmax.mrv.data.core;

import com.github.plplmax.mrv.domain.core.AppError;
import com.github.plplmax.mrv.domain.core.ErrorType;
import com.github.plplmax.mrv.domain.core.Mapper;

import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ErrorToDomainMapper implements Mapper<Throwable, AppError> {
    @Override
    public AppError map(Throwable t) {
        if (t instanceof UnknownHostException) return new AppError(ErrorType.NO_CONNECTION);
        else if (t instanceof HttpException) return new AppError(ErrorType.SERVICE_UNAVAILABLE);
        else return new AppError(ErrorType.GENERIC_ERROR);
    }
}
