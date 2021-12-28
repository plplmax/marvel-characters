package com.github.plplmax.mrv.ui.core;

import android.content.Context;

import androidx.annotation.StringRes;

public class ResourceProvider {
    private final Context context;

    public ResourceProvider(Context context) {
        this.context = context;
    }

    public String getString(@StringRes int id) {
        return context.getResources().getString(id);
    }
}
