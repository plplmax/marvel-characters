package com.github.plplmax.marvelcharacters.ui.core;

import androidx.annotation.NonNull;

import com.github.plplmax.marvelcharacters.R;
import com.github.plplmax.marvelcharacters.domain.core.ErrorType;
import com.github.plplmax.marvelcharacters.domain.core.Mapper;

public class ErrorToUiMapper implements Mapper<ErrorType, String> {
    private final ResourceProvider resourceProvider;

    public ErrorToUiMapper(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public String map(@NonNull ErrorType data) {
        int errorMessageId;

        if (data == ErrorType.NO_CONNECTION) {
            errorMessageId = R.string.no_internet_connection;
        } else if (data == ErrorType.SERVICE_UNAVAILABLE) {
            errorMessageId = R.string.service_unavailable;
        } else {
            errorMessageId = R.string.something_went_wrong;
        }

        return resourceProvider.getString(errorMessageId);
    }
}
